package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.models.DataRecord;
import org.junit.jupiter.api.Test;

import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.dataRecord;
import static com.arayby.ybymo.core.builders.TestDataRecordBuilder.entry;
import static org.assertj.core.api.Assertions.assertThat;

class CleanTransformerTest {

    @Test
    void transform_whenFieldsContainSpecialChars_removesAndStrips() {
        DataRecord dataRecord = dataRecord().field("col1", " @2.345-2 ").field("col2", "  abc  ").build();
        CleanTransformer transformer = new CleanTransformer();

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", "23452"), entry("col2", "abc"));
    }

    @Test
    void transform_whenFieldValueBlank_returnsEmptyString() {
        DataRecord dataRecord = dataRecord().field("col1", "   ").build();
        CleanTransformer transformer = new CleanTransformer();

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", ""));
    }

    @Test
    void transform_whenFieldValueNull_keepsNull() {
        DataRecord dataRecord = dataRecord().field("col1", null).build();
        CleanTransformer transformer = new CleanTransformer();

        DataRecord result = transformer.transform(dataRecord);

        assertThat(result.fields()).containsExactly(entry("col1", null));
    }
}
