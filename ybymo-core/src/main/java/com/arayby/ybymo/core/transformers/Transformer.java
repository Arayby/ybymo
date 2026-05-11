package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.models.DataRecord;

@FunctionalInterface
public interface Transformer {

    DataRecord transform(DataRecord record);
}
