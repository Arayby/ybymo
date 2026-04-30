package com.arayby.ybymo.core.extractor.csv;

import com.arayby.ybymo.core.extractor.Extractor;
import com.arayby.ybymo.core.model.DataRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class CsvExtractor implements Extractor<Integer> {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.builder().setIgnoreSurroundingSpaces(true).build();

    @Override
    public List<DataRecord> extract(Path file, List<Integer> selectors) throws IOException {
        try (CSVParser parser = FORMAT.parse(Files.newBufferedReader(file, StandardCharsets.UTF_8))) {
            return parser.stream().map(row -> {
                List<DataRecord.Field> fields = selectors.stream()
                                                         .filter(col -> col >= 1 && col <= row.size())
                                                         .map(col -> new DataRecord.Field("col" + col, row.get(col - 1)))
                                                         .toList();
                return DataRecord.of(fields);
            }).toList();
        }
    }
}
