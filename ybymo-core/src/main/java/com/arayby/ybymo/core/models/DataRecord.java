package com.arayby.ybymo.core.models;

import com.arayby.ybymo.core.messages.KeyMessage;
import com.arayby.ybymo.core.validators.ArgumentValidator;

import java.util.List;

public final class DataRecord {

    private final List<Field> fields;

    private DataRecord(List<Field> fields) {
        this.fields = List.copyOf(fields);
    }

    public static DataRecord of(List<Field> fields) {
        return new DataRecord(fields);
    }

    public List<Field> fields() {
        return fields;
    }

    public DataRecord withFields(List<Field> newFields) {
        return new DataRecord(newFields);
    }

    @Override
    public String toString() {
        return "DataRecord{fields=" + fields + "}";
    }

    public record Field(String name,
                        String value) {

        public Field {
            name = ArgumentValidator.requireNonBlank(name, KeyMessage.PARAMETER_FIELD_NAME);
        }

        public Field withValue(String newValue) {
            return new Field(name, newValue);
        }
    }
}
