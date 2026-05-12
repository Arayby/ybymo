package com.arayby.ybymo.build.tasks;

import com.arayby.ybymo.build.messages.BuildMessages;
import com.arayby.ybymo.build.supports.ChangelogSupport;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExtractVersionTask extends AbstractChangelogExtractionTask {

    private static final Logger logger = LoggerFactory.getLogger(ExtractVersionTask.class);

    @TaskAction
    public void extract() {
        String version = ChangelogSupport.findReleaseVersion(readChangelog());

        if (version == null) {
            throw new GradleException(BuildMessages.RELEASE_VERSION_NOT_FOUND);
        }

        writeOutput(version);

        getLogger().quiet(String.format(BuildMessages.VERSION_EXTRACTED, version));
        logger.info(version);
    }
}
