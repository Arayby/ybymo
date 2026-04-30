package com.arayby.ybymo.core.transformer;

import com.arayby.ybymo.core.model.DataRecord;

import java.util.List;
import java.util.Objects;

public final class PrefixTransformer implements Transformer {

    private final String prefix;

    public PrefixTransformer(String prefix) {
        this.prefix = Objects.requireNonNull(prefix, "Prefixo não pode ser nulo");
    }

    @Override
    public DataRecord transform(DataRecord record) {
        List<DataRecord.Field> prefixed = record.fields().stream().map(field -> field.withValue(switch (field.value()) {
            case null -> null;
            case String v -> prefix + v;
        })).toList();
        return record.withFields(prefixed);
    }
}
