package com.arayby.ybymo.core.transformer;

import com.arayby.ybymo.core.model.DataRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CleanTransformerTest {

    @Test
    void transform_whenFieldsContainSpecialChars_removesAndStrips() {
        DataRecord record = DataRecord.of(List.of(
                new DataRecord.Field("col1", " @2.345-2 "),
                new DataRecord.Field("col2", "  abc  ")
        ));
        CleanTransformer transformer = new CleanTransformer();

        DataRecord result = transformer.transform(record);

        assertThat(result.fields()).containsExactly(
                new DataRecord.Field("col1", "23452"),
                new DataRecord.Field("col2", "abc")
        );
    }

    @Test
    void transform_whenFieldValueBlank_returnsEmptyString() {
        DataRecord record = DataRecord.of(List.of(new DataRecord.Field("col1", "   ")));
        CleanTransformer transformer = new CleanTransformer();

        DataRecord result = transformer.transform(record);

        assertThat(result.fields()).containsExactly(new DataRecord.Field("col1", ""));
    }

    @Test
    void transform_whenFieldValueNull_keepsNull() {
        DataRecord record = DataRecord.of(List.of(new DataRecord.Field("col1", null)));
        CleanTransformer transformer = new CleanTransformer();

        DataRecord result = transformer.transform(record);

        assertThat(result.fields()).containsExactly(new DataRecord.Field("col1", null));
    }
}

