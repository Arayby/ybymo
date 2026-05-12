package com.arayby.ybymo.core.extractors.csv;

import com.arayby.ybymo.core.builders.CsvTestFileBuilder;
import com.arayby.ybymo.core.models.DataRecord;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvExtractorTest {

    @TempDir
    Path tempDir;

    @Test
    void extract_whenCsvHasMultipleRowsAndSelectedColumns_returnsMatchingRecords() throws IOException {
        Path file = CsvTestFileBuilder.csvIn(tempDir).row("name", "age", "city").row("Alice", "30", "London").row("Bob", "25", "Paris").build();

        List<DataRecord> records = new CsvExtractor().extract(file, List.of(1, 3));

        assertThat(records).hasSize(3);
        assertThat(records.get(0).fields()).containsExactly(entry("col1", "name"), entry("col3", "city"));
        assertThat(records.get(1).fields()).containsExactly(entry("col1", "Alice"), entry("col3", "London"));
        assertThat(records.get(2).fields()).containsExactly(entry("col1", "Bob"), entry("col3", "Paris"));
    }

    @Test
    void extract_whenSelectorContainsOnlyInvalidIndexes_returnsRecordsWithoutFields() throws IOException {
        Path file = CsvTestFileBuilder.csvIn(tempDir).row("a", "b", "c").row("d", "e", "f").build();

        List<DataRecord> records = new CsvExtractor().extract(file, List.of(0, -1, 4));

        assertThat(records).hasSize(2);
        assertThat(records.get(0).fields()).isEmpty();
        assertThat(records.get(1).fields()).isEmpty();
    }

    @Test
    void extract_whenCsvContainsInvalidUtf8Bytes_throwsIOException() throws IOException {
        Path file = tempDir.resolve("invalid.csv");
        Files.write(file, new byte[]{(byte) 0xC3, (byte) 0x28});
        ThrowingCallable thrown = () -> new CsvExtractor().extract(file, List.of(1));

        assertThatThrownBy(thrown).isInstanceOf(UncheckedIOException.class).hasCauseInstanceOf(MalformedInputException.class);
    }

    @Test
    void extract_whenSelectorIsNull_throwsNullPointerException() throws IOException {
        Path file = CsvTestFileBuilder.csvIn(tempDir).row("a", "b").row("c", "d").build();
        ThrowingCallable thrown = () -> new CsvExtractor().extract(file, null);

        assertThatThrownBy(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void extract_whenFileDoesNotExist_throwsIOException() {
        Path file = tempDir.resolve("missing.csv");

        assertThatThrownBy(() -> new CsvExtractor().extract(file, List.of(1))).isInstanceOf(IOException.class);
    }
}
