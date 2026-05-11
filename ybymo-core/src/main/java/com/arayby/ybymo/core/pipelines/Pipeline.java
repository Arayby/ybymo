package com.arayby.ybymo.core.pipelines;

import com.arayby.ybymo.core.models.DataRecord;
import com.arayby.ybymo.core.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Pipeline {

    private final Function<DataRecord, DataRecord> composed;

    private Pipeline(List<Transformer> transformers) {
        this.composed = transformers.stream().<Function<DataRecord, DataRecord>>map(t -> t::transform).reduce(Function.identity(), Function::andThen);
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<DataRecord> execute(List<DataRecord> records) {
        return records.stream().map(composed).toList();
    }

    public static final class Builder {

        private final List<Transformer> transformers = new ArrayList<>();

        public Builder add(Transformer transformer) {
            transformers.add(transformer);
            return this;
        }

        public Pipeline build() {
            return new Pipeline(List.copyOf(transformers));
        }
    }
}
