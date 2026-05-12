package com.arayby.ybymo.build.tasks;

import com.arayby.ybymo.build.messages.BuildMessages;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class AbstractChangelogExtractionTask extends DefaultTask {

    @InputFile
    public abstract RegularFileProperty getChangelogFile();

    @OutputFile
    public abstract RegularFileProperty getOutputFile();

    protected final String readChangelog() {
        var changelog = getChangelogFile().get().getAsFile();

        if (!changelog.exists()) {
            throw new GradleException(BuildMessages.CHANGELOG_NOT_FOUND);
        }

        try {
            return Files.readString(changelog.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new GradleException("Falha ao ler CHANGELOG.md", e);
        }
    }

    protected final void writeOutput(String content) {
        var output = getOutputFile().get().getAsFile();
        Path parent = output.toPath().getParent();

        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new GradleException(BuildMessages.COULD_NOT_CREATE_OUTPUT_DIR, e);
            }
        }

        try {
            Files.writeString(output.toPath(), content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new GradleException("Falha ao escrever arquivo de saída", e);
        }
    }
}
