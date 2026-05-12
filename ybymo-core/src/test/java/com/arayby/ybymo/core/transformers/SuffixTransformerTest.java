package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.models.DataRecord;
import org.junit.jupiter.api.Test;

import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.dataRecord;
import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SuffixTransformerTest {

    @Test
    void transform_whenFieldsHaveValues_addsSuffixToEachValue() {
        DataRecord dataRecord = dataRecord().field("col1", "a").field("col2", "b").build();
        SuffixTransformer transformer = new SuffixTransformer("-suf");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", "a-suf"), entry("col2", "b-suf"));
    }

    @Test
    void transform_whenFieldValueNull_keepsNull() {
        DataRecord dataRecord = dataRecord().field("col1", null).build();
        SuffixTransformer transformer = new SuffixTransformer("-suf");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", null));
    }

    @Test
    void constructor_whenSuffixNull_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new SuffixTransformer(null)).isInstanceOf(IllegalArgumentException.class).hasMessage("[Sufixo] não pode ser nulo");
    }
}
