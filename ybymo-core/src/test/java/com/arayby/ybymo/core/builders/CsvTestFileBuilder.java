package com.arayby.ybymo.core.builders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public final class CsvTestFileBuilder {

    private final Path tempDir;
    private final List<String[]> rows = new ArrayList<>();

    private CsvTestFileBuilder(Path tempDir) {
        this.tempDir = tempDir;
    }

    public static CsvTestFileBuilder csvIn(Path tempDir) {
        return new CsvTestFileBuilder(tempDir);
    }

    public CsvTestFileBuilder row(String... values) {
        rows.add(values);
        return this;
    }

    public Path build() throws IOException {
        String fileName = "input.csv";
        Path file = tempDir.resolve(fileName);
        StringJoiner joiner = new StringJoiner(System.lineSeparator(), "", System.lineSeparator());

        for (String[] row : rows) {
            joiner.add(String.join(",", row));
        }

        Files.writeString(file, joiner.toString());
        return file;
    }
}
