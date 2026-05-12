package com.arayby.ybymo.build.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.testing.jacoco.plugins.JacocoPlugin;
import org.gradle.testing.jacoco.tasks.JacocoReport;

public class JacocoConventionPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPluginManager().apply(JacocoPlugin.class);

        project.getTasks().withType(JacocoReport.class).configureEach(task -> {
            task.dependsOn(project.getTasks().getByName("test"));

            task.getReports().getXml().getRequired().set(true);
            task.getReports().getHtml().getRequired().set(true);
            task.getReports().getCsv().getRequired().set(false);
        });
    }
}
