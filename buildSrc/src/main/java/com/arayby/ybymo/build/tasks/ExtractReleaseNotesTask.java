package com.arayby.ybymo.build.tasks;

import com.arayby.ybymo.build.messages.BuildMessages;
import com.arayby.ybymo.build.supports.ChangelogSupport;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExtractReleaseNotesTask extends AbstractChangelogExtractionTask {

    private static final Logger logger = LoggerFactory.getLogger(ExtractReleaseNotesTask.class);

    @TaskAction
    public void extract() {
        String releaseNotes = ChangelogSupport.extractReleaseNotes(readChangelog());

        if (releaseNotes.isBlank()) {
            throw new GradleException(BuildMessages.RELEASE_NOTES_NOT_FOUND);
        }

        writeOutput(releaseNotes);

        getLogger().quiet(BuildMessages.RELEASE_NOTES_EXTRACTED);
        logger.info(releaseNotes);
    }
}
