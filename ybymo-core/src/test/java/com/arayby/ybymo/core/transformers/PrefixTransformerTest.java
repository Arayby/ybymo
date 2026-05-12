package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.models.DataRecord;
import org.junit.jupiter.api.Test;

import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.dataRecord;
import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrefixTransformerTest {

    @Test
    void transform_whenFieldsHaveValues_addsPrefixToEachValue() {
        DataRecord dataRecord = dataRecord().field("col1", "a").field("col2", "b").build();
        PrefixTransformer transformer = new PrefixTransformer("pre-");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", "pre-a"), entry("col2", "pre-b"));
    }

    @Test
    void transform_whenFieldValueNull_keepsNull() {
        DataRecord dataRecord = dataRecord().field("col1", null).build();
        PrefixTransformer transformer = new PrefixTransformer("pre-");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", null));
    }

    @Test
    void constructor_whenPrefixNull_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new PrefixTransformer(null)).isInstanceOf(IllegalArgumentException.class).hasMessage("[Prefixo] não pode ser nulo");
    }
}
