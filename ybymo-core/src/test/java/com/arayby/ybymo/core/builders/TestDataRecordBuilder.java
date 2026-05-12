package com.arayby.ybymo.core.builders;

import com.arayby.ybymo.core.models.DataRecord;

import java.util.ArrayList;
import java.util.List;

public final class TestDataRecordBuilder {

    private final List<DataRecord.Field> fields = new ArrayList<>();

    public static TestDataRecordBuilder dataRecord() {
        return new TestDataRecordBuilder();
    }

    public static DataRecord.Field entry(String name, String value) {
        return new DataRecord.Field(name, value);
    }

    public TestDataRecordBuilder field(String name, String value) {
        fields.add(new DataRecord.Field(name, value));
        return this;
    }

    public DataRecord build() {
        return DataRecord.of(fields);
    }
}
