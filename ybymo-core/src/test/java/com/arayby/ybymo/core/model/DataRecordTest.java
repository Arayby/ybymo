package com.arayby.ybymo.core.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataRecordTest {

    @Test
    void withFields_whenNewFieldsProvided_returnsNewRecord() {
        DataRecord original = DataRecord.of(List.of(new DataRecord.Field("col1", "value1")));
        List<DataRecord.Field> newFields = List.of(new DataRecord.Field("col2", "value2"));

        DataRecord updated = original.withFields(newFields);

        assertThat(updated).isNotSameAs(original);
        assertThat(original.fields()).containsExactly(new DataRecord.Field("col1", "value1"));
        assertThat(updated.fields()).containsExactly(new DataRecord.Field("col2", "value2"));
    }

    @Test
    void toString_whenFieldsPresent_returnsFormattedString() {
        DataRecord record = DataRecord.of(List.of(new DataRecord.Field("col1", "value1")));

        String result = record.toString();

        assertThat(result).isEqualTo("DataRecord[Field[name=col1, value=value1]]");
    }

    @Test
    void Field_whenNameNull_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new DataRecord.Field(null, "value")).isInstanceOf(IllegalArgumentException.class).hasMessage("Nome do campo não pode ser vazio");
    }

    @Test
    void Field_whenNameBlank_throwsIllegalArgumentException() {
        String name = "   ";

        assertThatThrownBy(() -> new DataRecord.Field(name, "value")).isInstanceOf(IllegalArgumentException.class).hasMessage("Nome do campo não pode ser vazio");
    }

    @Test
    void withValue_whenNewValueProvided_returnsFieldWithSameName() {
        DataRecord.Field field = new DataRecord.Field("col1", "value1");

        DataRecord.Field updated = field.withValue("value2");

        assertThat(updated).isEqualTo(new DataRecord.Field("col1", "value2"));
        assertThat(updated.name()).isEqualTo("col1");
    }
}
