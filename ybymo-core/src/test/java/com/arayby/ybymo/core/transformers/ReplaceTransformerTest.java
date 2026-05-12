package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.models.DataRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.dataRecord;
import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReplaceTransformerTest {

    @Test
    void transform_whenFieldContainsTargetChar_replacesValue() {
        DataRecord dataRecord = dataRecord().field("col1", "a").field("col2", "b").build();
        ReplaceTransformer transformer = new ReplaceTransformer("a", "c");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", "c"), entry("col2", "b"));
    }

    @Test
    void transform_whenFieldContainsSubstring_replacesValue() {
        DataRecord dataRecord = dataRecord().field("col1", "lorem ipsum").field("col2", "dolor sit amet").build();
        ReplaceTransformer transformer = new ReplaceTransformer("r sit a", "_<>_");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", "lorem ipsum"), entry("col2", "dolo_<>_met"));
    }

    @Test
    void transform_whenReplacementIsEmptyString_replacesWithEmpty() {
        DataRecord dataRecord = dataRecord().field("col1", "a").field("col2", "b").build();
        ReplaceTransformer transformer = new ReplaceTransformer("a", "");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", ""), entry("col2", "b"));
    }

    @Test
    void transform_whenTargetTextIsSpaces_replacesAllSpaces() {
        DataRecord dataRecord = dataRecord().field("col1", "a b c").field("col2", "x y z").build();
        ReplaceTransformer transformer = new ReplaceTransformer(" ", "a");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", "aabac"), entry("col2", "xayaz"));
    }

    @Test
    void transform_whenFieldsContainsNullAndMatchingValue_replacesValue() {
        DataRecord dataRecord = dataRecord().field("col1", null).field("col2", "abc").build();
        ReplaceTransformer transformer = new ReplaceTransformer("a", "z");

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", null), entry("col2", "zbc"));
    }

    @Test
    void transform_whenTargetTextNotFoundInFields_throwsIllegalArgumentException() {
        DataRecord dataRecord = DataRecord.of(List.of(new DataRecord.Field("col1", "abc"), new DataRecord.Field("col2", "xyz")));
        ReplaceTransformer transformer = new ReplaceTransformer("1", "2");

        assertThatThrownBy(() -> transformer.transform(dataRecord)).isInstanceOf(IllegalArgumentException.class).hasMessage("[1] não foi encontrado");
    }

    @Test
    void constructor_whenTextToReplaceIsEmpty_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new ReplaceTransformer("", "c")).isInstanceOf(IllegalArgumentException.class)
                                                                 .hasMessage("[Texto a ser substituído] não pode estar vazio");
    }

    @Test
    void constructor_whenTextToReplaceIsNull_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new ReplaceTransformer(null, "c")).isInstanceOf(IllegalArgumentException.class)
                                                                   .hasMessage("[Texto a ser substituído] não pode estar vazio");
    }

    @Test
    void constructor_whenReplacementTextIsNull_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new ReplaceTransformer("a", null)).isInstanceOf(IllegalArgumentException.class)
                                                                   .hasMessage("[Texto de substituição] não pode ser nulo");
    }
}
