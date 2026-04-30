package com.arayby.ybymo.core.transformer;

import com.arayby.ybymo.core.model.DataRecord;

import java.util.List;
import java.util.regex.Pattern;

public final class CleanTransformer implements Transformer {

    private static final Pattern CHARS_TO_REMOVE = Pattern.compile("[^a-zA-ZÀ-ÿ0-9\\s]");

    @Override
    public DataRecord transform(DataRecord record) {
        List<DataRecord.Field> cleaned = record.fields().stream().map(field -> field.withValue(clean(field.value()))).toList();
        return record.withFields(cleaned);
    }

    private String clean(String value) {
        return switch (value) {
            case null -> null;
            case String v when v.isBlank() -> "";
            case String v -> CHARS_TO_REMOVE.matcher(v).replaceAll("").strip();
        };
    }
}
