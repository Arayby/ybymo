package com.arayby.ybymo.build.plugins;

import com.arayby.ybymo.build.messages.BuildMessages;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.stream.Collectors;

public class SonarCloudConventionPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPluginManager().apply("org.sonarqube");

        project.getExtensions().configure("sonarqube", sonarExt -> configureSonarProperties(project, sonarExt));
    }

    private void configureSonarProperties(Project project, Object sonarExt) {
        var sonarClass = sonarExt.getClass();
        try {
            var propertiesMethod = sonarClass.getMethod("properties", Action.class);

            propertiesMethod.invoke(sonarExt, (Action<?>) propertiesObj -> {
                addProperty(propertiesObj, "sonar.projectKey", System.getenv("SONAR_PROJECT_KEY"));
                addProperty(propertiesObj, "sonar.organization", System.getenv("SONAR_ORGANIZATION"));
                addProperty(propertiesObj, "sonar.host.url", "https://sonarcloud.io");
                addProperty(propertiesObj, "sonar.sourceEncoding", "UTF-8");

                var jacocoPaths = project.getSubprojects()
                                         .stream()
                                         .map(sub -> sub.getLayout()
                                                        .getBuildDirectory()
                                                        .file("reports/jacoco/test/jacocoTestReport.xml")
                                                        .get()
                                                        .getAsFile()
                                                        .getAbsolutePath())
                                         .collect(Collectors.joining(","));
                addProperty(propertiesObj, "sonar.coverage.jacoco.xmlReportPaths", jacocoPaths);

                var binariesPaths = project.getSubprojects()
                                           .stream()
                                           .map(sub -> sub.getLayout().getBuildDirectory().file("classes/java/main").get().getAsFile().getAbsolutePath())
                                           .collect(Collectors.joining(","));
                addProperty(propertiesObj, "sonar.java.binaries", binariesPaths);

                var prKey = System.getenv("SONAR_PULLREQUEST_KEY");
                if (prKey != null && !prKey.isEmpty()) {
                    addProperty(propertiesObj, "sonar.pullrequest.key", prKey);
                    addProperty(propertiesObj, "sonar.pullrequest.branch", System.getenv("SONAR_PULLREQUEST_BRANCH"));
                    addProperty(propertiesObj, "sonar.pullrequest.base", System.getenv("SONAR_PULLREQUEST_BASE"));
                }
            });
        } catch (ReflectiveOperationException e) {
            throw new GradleException(BuildMessages.SONAR_PROPERTIES_CONFIG_FAILED, e);
        }
    }

    private void addProperty(Object propertiesObj, String key, Object value) {
        try {
            var propertyMethod = propertiesObj.getClass().getMethod("property", String.class, Object.class);
            propertyMethod.invoke(propertiesObj, key, value);
        } catch (ReflectiveOperationException e) {
            throw new GradleException(String.format(BuildMessages.SONAR_PROPERTY_SET_FAILED, key), e);
        }
    }
}
