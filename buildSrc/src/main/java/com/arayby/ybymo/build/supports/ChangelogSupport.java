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
        String[] lines = text.split("\n", -1);

        int start = -1;
        for (int i = 0; i < lines.length; i++) {
            if (RELEASE_SECTION_START_PATTERN.matcher(lines[i]).find()) {
                start = i;
                break;
            }
        }

        if (start < 0) {
            return "";
        }

        int end = lines.length;
        for (int i = start + 1; i < lines.length; i++) {
            if (lines[i].matches("^## \\[.*")) {
                end = i;
                break;
            }
        }

        StringBuilder out = new StringBuilder();
        for (int i = start; i < end; i++) {
            out.append(lines[i]).append('\n');
        }

        return out.toString().trim();
    }
}
