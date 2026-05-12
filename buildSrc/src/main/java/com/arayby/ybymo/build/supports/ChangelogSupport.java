package com.arayby.ybymo.build.supports;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChangelogSupport {

    public static final Pattern SNAPSHOT_HEADER_PATTERN = Pattern.compile("^## \\[((\\d+\\.\\d+\\.\\d+|X\\.Y\\.Z)-SNAPSHOT)]");
    public static final Pattern SNAPSHOT_SECTION_PATTERN = Pattern.compile("^## \\[((\\d+\\.\\d+\\.\\d+|X\\.Y\\.Z)-SNAPSHOT)]");
    public static final Pattern RELEASE_VERSION_PATTERN = Pattern.compile("^## \\[(\\d+\\.\\d+\\.\\d+)]");
    public static final Pattern RELEASE_SECTION_START_PATTERN = Pattern.compile("^## \\[\\d+\\.\\d+\\.\\d+]");

    private ChangelogSupport() {
    }

    public static String findTopSnapshotVersion(String text) {
        for (String line : text.split("\n")) {
            Matcher matcher = SNAPSHOT_HEADER_PATTERN.matcher(line);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static String findSnapshotSectionVersion(String text) {
        for (String line : text.split("\n")) {
            Matcher matcher = SNAPSHOT_SECTION_PATTERN.matcher(line);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static String findReleaseVersion(String text) {
        for (String line : text.split("\n")) {
            Matcher matcher = RELEASE_VERSION_PATTERN.matcher(line);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static String extractReleaseNotes(String text) {
        String[] lines = text.split("\n");
        StringBuilder releaseNotes = new StringBuilder();
        boolean captureNotes = false;
        boolean firstReleaseSectionFound = false;
        boolean stopCapture = false;

        for (String line : lines) {
            if (!stopCapture && isReleaseSectionStart(line)) {
                if (!firstReleaseSectionFound) {
                    captureNotes = true;
                    firstReleaseSectionFound = true;
                } else {
                    stopCapture = true;
                }
            }

            if (!stopCapture && captureNotes && isCaptureStopMarker(line)) {
                stopCapture = true;
            }

            if (!stopCapture && captureNotes) {
                appendLine(releaseNotes, line);
            }
        }

        return releaseNotes.toString().trim();
    }

    private static boolean isReleaseSectionStart(String line) {
        return RELEASE_SECTION_START_PATTERN.matcher(line).find();
    }

    private static boolean isCaptureStopMarker(String line) {
        return "---".equals(line) || line.matches("^## \\[.*");
    }

    private static void appendLine(StringBuilder releaseNotes, String line) {
        if (!line.trim().isEmpty()) {
            releaseNotes.append(line);
        }
        releaseNotes.append('\n');
    }
}
