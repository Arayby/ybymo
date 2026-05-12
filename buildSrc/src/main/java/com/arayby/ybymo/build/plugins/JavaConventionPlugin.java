package com.arayby.ybymo.build.plugins;

import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.testing.Test;

public class JavaConventionPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPluginManager().apply("java");

        var java = project.getExtensions().getByType(JavaPluginExtension.class);
        java.setSourceCompatibility(JavaVersion.VERSION_25);
        java.setTargetCompatibility(JavaVersion.VERSION_25);

        project.getRepositories().mavenCentral();

        project.getTasks().withType(Test.class).configureEach(Test::useJUnitPlatform);
        project.getTasks().withType(JavaCompile.class).configureEach(task -> {
            task.getOptions().setEncoding("UTF-8");
            task.getOptions().getCompilerArgs().add("--enable-preview");
        });
        project.getTasks().withType(Test.class).configureEach(test -> test.jvmArgs("--enable-preview"));
    }
}
