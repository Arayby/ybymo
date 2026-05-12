package com.arayby.ybymo.core.validators;

import com.arayby.ybymo.core.messages.CatalogMessage;
import com.arayby.ybymo.core.messages.KeyMessage;

import java.util.List;

public final class ArgumentValidator {

    private ArgumentValidator() {
        throw new AssertionError(CatalogMessage.resolve(KeyMessage.UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED));
    }

    public static String requireNonNull(String value, KeyMessage parameterKey) {
        if (value == null) {
            throw new IllegalArgumentException(CatalogMessage.format(KeyMessage.ERROR_OBJECT_NON_NULL, parameterKey));
        }
        return value;
    }

    public static String requireNonBlank(String value, KeyMessage parameterKey) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(CatalogMessage.format(KeyMessage.ERROR_STRING_NON_BLANK, parameterKey));
        }
        return value;
    }

    public static String requireNonEmpty(String value, KeyMessage parameterKey) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(CatalogMessage.format(KeyMessage.ERROR_STRING_NON_EMPTY, parameterKey));
        }
        return value;
    }

    public static void requireExistsInAny(String textToFind, List<String> values) {
        requireNonEmpty(textToFind, KeyMessage.ERROR_STRING_NON_EMPTY);
        if (values.stream().noneMatch(value -> value != null && value.contains(textToFind))) {
            throw new IllegalArgumentException(CatalogMessage.format(KeyMessage.ERROR_TEXT_NOT_FOUND, textToFind));
        }
    }
}
