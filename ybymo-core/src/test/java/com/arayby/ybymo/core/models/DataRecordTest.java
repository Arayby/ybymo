package com.arayby.ybymo.core.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.dataRecord;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataRecordTest {

    @Test
    void withFields_whenNewFieldsProvided_returnsNewRecord() {
        DataRecord original = dataRecord().field("col1", "value1").build();
        List<DataRecord.Field> newFields = List.of(new DataRecord.Field("col2", "value2"));

        DataRecord updated = original.withFields(newFields);

        assertThat(updated).isNotSameAs(original);
        assertThat(original.fields()).containsExactly(new DataRecord.Field("col1", "value1"));
        assertThat(updated.fields()).containsExactly(new DataRecord.Field("col2", "value2"));
    }

    @Test
    void toString_whenFieldsPresent_returnsFormattedString() {
        DataRecord dataRecord = dataRecord().field("col1", "value1").build();

        String result = dataRecord.toString();

        assertThat(result).isEqualTo("DataRecord{fields=[Field[name=col1, value=value1]]}");
    }

    @Test
    void constructor_whenFieldNameNull_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new DataRecord.Field(null, "value")).isInstanceOf(IllegalArgumentException.class)
                                                                     .hasMessage("[Nome do campo] não pode estar em branco");
    }

    @Test
    void constructor_whenFieldNameBlank_throwsIllegalArgumentException() {
        String name = "   ";

        assertThatThrownBy(() -> new DataRecord.Field(name, "value")).isInstanceOf(IllegalArgumentException.class)
                                                                     .hasMessage("[Nome do campo] não pode estar em branco");
    }

    @Test
    void withValue_whenNewValueProvided_returnsFieldWithSameName() {
        DataRecord.Field field = new DataRecord.Field("col1", "value1");

        DataRecord.Field updated = field.withValue("value2");

        assertThat(updated).isEqualTo(new DataRecord.Field("col1", "value2"));
        assertThat(updated.name()).isEqualTo("col1");
    }
}
