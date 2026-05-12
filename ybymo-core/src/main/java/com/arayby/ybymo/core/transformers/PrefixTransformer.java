package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.messages.KeyMessage;
import com.arayby.ybymo.core.models.DataRecord;
import com.arayby.ybymo.core.validators.ArgumentValidator;

import java.util.List;

public final class PrefixTransformer implements Transformer {

    private final String prefix;

    public PrefixTransformer(String prefix) {
        this.prefix = ArgumentValidator.requireNonNull(prefix, KeyMessage.PARAMETER_PREFIX);
    }

    @Override
    public DataRecord transform(DataRecord dataRecord) {
        List<DataRecord.Field> prefixed = dataRecord.fields().stream().map(field -> field.withValue(switch (field.value()) {
            case null -> null;
            case String v -> prefix + v;
        })).toList();
        return dataRecord.withFields(prefixed);
    }
}
