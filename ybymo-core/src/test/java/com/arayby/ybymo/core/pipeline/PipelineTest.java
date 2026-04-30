package com.arayby.ybymo.core.pipeline;

import com.arayby.ybymo.core.model.DataRecord;
import com.arayby.ybymo.core.transformer.Transformer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PipelineTest {

    private static DataRecord appendSuffix(DataRecord record, String suffix) {
        DataRecord.Field field = record.fields().getFirst();
        return record.withFields(List.of(field.withValue(field.value() + suffix)));
    }

    @Test
    void execute_whenTransformersPresent_appliesInOrder() {
        DataRecord record = DataRecord.of(List.of(new DataRecord.Field("col1", "a")));
        Transformer first = r -> appendSuffix(r, "-first");
        Transformer second = r -> appendSuffix(r, "-second");
        Pipeline pipeline = Pipeline.builder().add(first).add(second).build();

        List<DataRecord> result = pipeline.execute(List.of(record));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().fields()).containsExactly(new DataRecord.Field("col1", "a-first-second"));
    }

    @Test
    void execute_whenNoTransformers_returnsRecordsWithSameFields() {
        DataRecord record = DataRecord.of(List.of(new DataRecord.Field("col1", "value")));
        Pipeline pipeline = Pipeline.builder().build();

        List<DataRecord> result = pipeline.execute(List.of(record));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().fields()).containsExactly(new DataRecord.Field("col1", "value"));
    }

    @Test
    void execute_whenRecordsEmpty_returnsEmptyList() {
        Pipeline pipeline = Pipeline.builder().build();

        List<DataRecord> result = pipeline.execute(List.of());

        assertThat(result).isEmpty();
    }
}
