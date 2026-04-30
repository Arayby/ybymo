package com.arayby.ybymo.core.transformer;

import com.arayby.ybymo.core.model.DataRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SuffixTransformerTest {

    @Test
    void transform_whenFieldsHaveValues_addsSuffixToEachValue() {
        DataRecord record = DataRecord.of(List.of(
                new DataRecord.Field("col1", "a"),
                new DataRecord.Field("col2", "b")
        ));
        SuffixTransformer transformer = new SuffixTransformer("-suf");

        DataRecord result = transformer.transform(record);

        assertThat(result.fields()).containsExactly(
                new DataRecord.Field("col1", "a-suf"),
                new DataRecord.Field("col2", "b-suf")
        );
    }

    @Test
    void transform_whenFieldValueNull_keepsNull() {
        DataRecord record = DataRecord.of(List.of(new DataRecord.Field("col1", null)));
        SuffixTransformer transformer = new SuffixTransformer("-suf");

        DataRecord result = transformer.transform(record);

        assertThat(result.fields()).containsExactly(new DataRecord.Field("col1", null));
    }

    @Test
    void SuffixTransformer_whenSuffixNull_throwsException() {
        assertThatThrownBy(() -> new SuffixTransformer(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Sufixo não pode ser nulo");
    }
}

