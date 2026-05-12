package com.arayby.ybymo.build.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import com.arayby.ybymo.build.tasks.ExtractReleaseNotesTask;
import com.arayby.ybymo.build.tasks.ExtractVersionTask;
import com.arayby.ybymo.build.tasks.ValidateChangelogTask;
import com.arayby.ybymo.build.tasks.ValidateVersionSyncTask;

public class TasksConventionPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        var projectChangelogPath = project.getProjectDir().toPath().resolve("CHANGELOG.md");
        var changelogFile = projectChangelogPath.toFile().exists()
            ? project.getLayout().getProjectDirectory().file(projectChangelogPath.toString())
            : project.getRootProject().getLayout().getProjectDirectory().file("CHANGELOG.md");

        var buildFilePath = project.getProjectDir().toPath().resolve("build.gradle");
        var buildFile = buildFilePath.toFile().exists()
            ? project.getLayout().getProjectDirectory().file(buildFilePath.toString())
            : project.getRootProject().getLayout().getProjectDirectory().file("build.gradle");

        project.getTasks().register("validateChangelog", ValidateChangelogTask.class, task ->
            task.getChangelogFile().set(changelogFile)
        );

        project.getTasks().register("validateVersionSync", ValidateVersionSyncTask.class, task -> {
            task.getChangelogFile().set(changelogFile);
            task.getBuildFile().set(buildFile);
        });

        project.getTasks().register("extractVersion", ExtractVersionTask.class, task -> {
            task.getChangelogFile().set(changelogFile);
            task.getOutputFile().set(project.getLayout().getBuildDirectory().file("version.txt"));
        });

        project.getTasks().register("extractReleaseNotes", ExtractReleaseNotesTask.class, task -> {
            task.getChangelogFile().set(changelogFile);
            task.getOutputFile().set(project.getLayout().getBuildDirectory().file("release-notes.txt"));
        });
    }
}






