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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValidateVersionSyncTask extends DefaultTask {

    private static String readText(java.io.File file) {
        try {
            return Files.readString(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new GradleException("Falha ao ler arquivo para validação de versão", e);
        }
    }

    private static String findBuildVersion(String text) {
        Pattern buildPattern = Pattern.compile("version\\s*=\\s*['\"]([^'\"]+)['\"]");
        Matcher matcher = buildPattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @InputFile
    public abstract RegularFileProperty getChangelogFile();

    @InputFile
    public abstract RegularFileProperty getBuildFile();

    @TaskAction
    public void validate() {
        var changelog = getChangelogFile().get().getAsFile();
        var buildFile = getBuildFile().get().getAsFile();

        if (!changelog.exists()) {
            throw new GradleException(BuildMessages.CHANGELOG_NOT_FOUND);
        }

        if (!buildFile.exists()) {
            throw new GradleException(BuildMessages.BUILD_FILE_NOT_FOUND);
        }

        String changelogSnapshotVersion = ChangelogSupport.findSnapshotSectionVersion(readText(changelog));
        String buildVersion = findBuildVersion(readText(buildFile));

        if (changelogSnapshotVersion == null) {
            throw new GradleException(BuildMessages.SNAPSHOT_SECTION_NOT_FOUND);
        }

        if (buildVersion == null) {
            throw new GradleException(BuildMessages.VERSION_NOT_FOUND);
        }

        if ("X.Y.Z-SNAPSHOT".equals(changelogSnapshotVersion)) {
            getLogger().quiet(String.format(BuildMessages.SNAPSHOT_PLACEHOLDER_SYNC, changelogSnapshotVersion));
            return;
        }

        if (!buildVersion.equals(changelogSnapshotVersion)) {
            throw new GradleException(String.format(BuildMessages.VERSIONS_MISMATCH, changelogSnapshotVersion, buildVersion));
        }

        getLogger().quiet(String.format(BuildMessages.VERSION_SYNC_OK, changelogSnapshotVersion, buildVersion));
    }
}
