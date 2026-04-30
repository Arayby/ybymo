package com.arayby.ybymo.core.transformer;

import com.arayby.ybymo.core.model.DataRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrefixTransformerTest {

    @Test
    void transform_whenFieldsHaveValues_addsPrefixToEachValue() {
        DataRecord record = DataRecord.of(List.of(
                new DataRecord.Field("col1", "a"),
                new DataRecord.Field("col2", "b")
        ));
        PrefixTransformer transformer = new PrefixTransformer("pre-");

        DataRecord result = transformer.transform(record);

        assertThat(result.fields()).containsExactly(
                new DataRecord.Field("col1", "pre-a"),
                new DataRecord.Field("col2", "pre-b")
        );
    }

    @Test
    void transform_whenFieldValueNull_keepsNull() {
        DataRecord record = DataRecord.of(List.of(new DataRecord.Field("col1", null)));
        PrefixTransformer transformer = new PrefixTransformer("pre-");

        DataRecord result = transformer.transform(record);

        assertThat(result.fields()).containsExactly(new DataRecord.Field("col1", null));
    }

    @Test
    void PrefixTransformer_whenPrefixNull_throwsException() {
        assertThatThrownBy(() -> new PrefixTransformer(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Prefixo não pode ser nulo");
    }
}

