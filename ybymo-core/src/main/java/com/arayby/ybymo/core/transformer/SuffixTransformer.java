package com.arayby.ybymo.core.transformer;

import com.arayby.ybymo.core.model.DataRecord;

import java.util.List;
import java.util.Objects;

public final class SuffixTransformer implements Transformer {

    private final String suffix;

    public SuffixTransformer(String suffix) {
        this.suffix = Objects.requireNonNull(suffix, "Sufixo não pode ser nulo");
    }

    @Override
    public DataRecord transform(DataRecord record) {
        List<DataRecord.Field> suffixed = record.fields().stream().map(field -> field.withValue(switch (field.value()) {
            case null -> null;
            case String v -> v + suffix;
        })).toList();
        return record.withFields(suffixed);
    }
}
