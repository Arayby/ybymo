package com.arayby.ybymo.core.model;

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
        return "DataRecord" + fields;
    }

    public record Field(String name,
                        String value) {

        public Field {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Nome do campo não pode ser vazio");
            }
        }

        public Field withValue(String newValue) {
            return new Field(name, newValue);
        }
    }
}
