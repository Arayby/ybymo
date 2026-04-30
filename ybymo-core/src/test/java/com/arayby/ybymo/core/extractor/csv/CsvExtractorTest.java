package com.arayby.ybymo.core.extractor.csv;

import com.arayby.ybymo.core.model.DataRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvExtractorTest {

    @TempDir
    Path tempDir;

    @Test
    void extract_whenCsvHasMultipleRowsAndSelectedColumns_returnsMatchingRecords() throws IOException {
        Path file = tempDir.resolve("input.csv");
        Files.writeString(file, """
                                name,age,city
                                Alice,30,London
                                Bob,25,Paris
                                """);

        List<DataRecord> records = new CsvExtractor().extract(file, List.of(1, 3));

        assertThat(records).hasSize(3);
        assertThat(records.get(0).fields()).containsExactly(new DataRecord.Field("col1", "name"), new DataRecord.Field("col3", "city"));
        assertThat(records.get(1).fields()).containsExactly(new DataRecord.Field("col1", "Alice"), new DataRecord.Field("col3", "London"));
        assertThat(records.get(2).fields()).containsExactly(new DataRecord.Field("col1", "Bob"), new DataRecord.Field("col3", "Paris"));
    }

    @Test
    void extract_whenSelectorContainsOnlyInvalidIndexes_returnsRecordsWithoutFields() throws IOException {
        Path file = tempDir.resolve("input.csv");
        Files.writeString(file, """
                                a,b,c
                                d,e,f
                                """);

        List<DataRecord> records = new CsvExtractor().extract(file, List.of(0, -1, 4));

        assertThat(records).hasSize(2);
        assertThat(records.get(0).fields()).isEmpty();
        assertThat(records.get(1).fields()).isEmpty();
    }

    @Test
    void extract_whenCsvContainsInvalidUtf8Bytes_throwsIOException() throws IOException {
        Path file = tempDir.resolve("invalid.csv");
        Files.write(file, new byte[]{(byte) 0xC3, (byte) 0x28});

        assertThatThrownBy(() -> new CsvExtractor().extract(file, List.of(1))).isInstanceOf(UncheckedIOException.class).hasCauseInstanceOf(java.nio.charset.MalformedInputException.class);
    }

    @Test
    void extract_whenSelectorsIsNull_throwsNullPointerException() throws IOException {
        Path file = tempDir.resolve("input.csv");
        Files.writeString(file, """
                                a,b
                                c,d
                                """);

        assertThatThrownBy(() -> new CsvExtractor().extract(file, null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void extract_whenFileDoesNotExist_throwsIOException() {
        Path file = tempDir.resolve("missing.csv");

        assertThatThrownBy(() -> new CsvExtractor().extract(file, List.of(1))).isInstanceOf(IOException.class);
    }
}
