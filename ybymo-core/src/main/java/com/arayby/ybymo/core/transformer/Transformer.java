package com.arayby.ybymo.core.transformer;

import com.arayby.ybymo.core.model.DataRecord;

@FunctionalInterface
public interface Transformer {

    DataRecord transform(DataRecord record);
}
