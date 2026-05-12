package com.arayby.ybymo.core.pipelines;

import com.arayby.ybymo.core.models.DataRecord;
import com.arayby.ybymo.core.transformers.Transformer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.dataRecord;
import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.entry;
import static org.assertj.core.api.Assertions.assertThat;

class PipelineTest {

    private static DataRecord appendSuffix(DataRecord dataRecord, String suffix) {
        DataRecord.Field field = dataRecord.fields().getFirst();
        return dataRecord.withFields(List.of(field.withValue(field.value() + suffix)));
    }

    @Test
    void execute_whenTransformersPresent_appliesInOrder() {
        DataRecord dataRecord = dataRecord().field("col1", "a").build();
        Transformer first = r -> appendSuffix(r, "-first");
        Transformer second = r -> appendSuffix(r, "-second");
        Pipeline pipeline = Pipeline.builder().add(first).add(second).build();

        List<DataRecord> result = pipeline.execute(List.of(dataRecord));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().fields()).containsExactly(entry("col1", "a-first-second"));
    }

    @Test
    void execute_whenNoTransformers_returnsRecordsWithSameFields() {
        DataRecord dataRecord = dataRecord().field("col1", "value").build();
        Pipeline pipeline = Pipeline.builder().build();

        List<DataRecord> result = pipeline.execute(List.of(dataRecord));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().fields()).containsExactly(entry("col1", "value"));
    }

    @Test
    void execute_whenRecordsEmpty_returnsEmptyList() {
        Pipeline pipeline = Pipeline.builder().build();

        List<DataRecord> result = pipeline.execute(List.of());

        assertThat(result).isEmpty();
    }
}
