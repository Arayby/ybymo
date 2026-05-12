package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.models.DataRecord;

import java.util.List;
import java.util.regex.Pattern;

public final class CleanTransformer implements Transformer {

    private static final Pattern CHARS_TO_REMOVE = Pattern.compile("[^a-zA-ZÀ-ÿ0-9\\s]");

    @Override
    public DataRecord transform(DataRecord dataRecord) {
        List<DataRecord.Field> cleaned = dataRecord.fields().stream().map(field -> field.withValue(clean(field.value()))).toList();
        return dataRecord.withFields(cleaned);
    }

    private String clean(String value) {
        return switch (value) {
            case null -> null;
            case String v when v.isBlank() -> "";
            case String v -> CHARS_TO_REMOVE.matcher(v).replaceAll("").strip();
        };
    }
}
