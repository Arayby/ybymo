package com.arayby.ybymo.build.tasks;

import com.arayby.ybymo.build.messages.BuildMessages;
import com.arayby.ybymo.build.supports.ChangelogSupport;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public abstract class ValidateChangelogTask extends DefaultTask {

    private static String readText(java.io.File file) {
        try {
            return Files.readString(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new GradleException("Falha ao ler CHANGELOG.md", e);
        }
    }

    @InputFile
    public abstract RegularFileProperty getChangelogFile();

    @TaskAction
    public void validate() {
        var changelog = getChangelogFile().get().getAsFile();

        if (!changelog.exists()) {
            throw new GradleException(BuildMessages.CHANGELOG_NOT_FOUND);
        }

        String topSnapshotVersion = ChangelogSupport.findTopSnapshotVersion(readText(changelog));

        if (topSnapshotVersion == null) {
            throw new GradleException(BuildMessages.CHANGELOG_MUST_START_WITH_SNAPSHOT);
        }

        getLogger().quiet(String.format(BuildMessages.CHANGELOG_VALIDATED, topSnapshotVersion));
    }
}
