package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.messages.KeyMessage;
import com.arayby.ybymo.core.models.DataRecord;
import com.arayby.ybymo.core.validators.ArgumentValidator;

import java.util.List;

public final class SuffixTransformer implements Transformer {

    private final String suffix;

    public SuffixTransformer(String suffix) {
        this.suffix = ArgumentValidator.requireNonNull(suffix, KeyMessage.PARAMETER_SUFFIX);
    }

    @Override
    public DataRecord transform(DataRecord dataRecord) {
        List<DataRecord.Field> suffixed = dataRecord.fields().stream().map(field -> field.withValue(switch (field.value()) {
            case null -> null;
            case String v -> v + suffix;
        })).toList();
        return dataRecord.withFields(suffixed);
    }
}
