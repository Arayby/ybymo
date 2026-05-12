package com.arayby.ybymo.core.messages;

import java.util.EnumMap;
import java.util.Map;

public final class CatalogMessage {

    private static final Map<KeyMessage, String> MESSAGE_BY_KEY = new EnumMap<>(KeyMessage.class);

    static {

        // Utilities
        MESSAGE_BY_KEY.put(KeyMessage.UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED, "Classe utilitária não deve ser instanciada");

        // Parameters
        MESSAGE_BY_KEY.put(KeyMessage.PARAMETER_FIELD_NAME, "Nome do campo");
        MESSAGE_BY_KEY.put(KeyMessage.PARAMETER_PREFIX, "Prefixo");
        MESSAGE_BY_KEY.put(KeyMessage.PARAMETER_SUFFIX, "Sufixo");
        MESSAGE_BY_KEY.put(KeyMessage.PARAMETER_TEXT_TO_REPLACE, "Texto a ser substituído");
        MESSAGE_BY_KEY.put(KeyMessage.PARAMETER_REPLACEMENT_TEXT, "Texto de substituição");

        // Errors
        MESSAGE_BY_KEY.put(KeyMessage.ERROR_OBJECT_NON_NULL, "[%s] não pode ser nulo");
        MESSAGE_BY_KEY.put(KeyMessage.ERROR_STRING_NON_BLANK, "[%s] não pode estar em branco");
        MESSAGE_BY_KEY.put(KeyMessage.ERROR_STRING_NON_EMPTY, "[%s] não pode estar vazio");
        MESSAGE_BY_KEY.put(KeyMessage.ERROR_TEXT_NOT_FOUND, "[%s] não foi encontrado");
    }

    private CatalogMessage() {
        throw new AssertionError(resolve(KeyMessage.UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED));
    }

    public static String resolve(KeyMessage key) {
        return MESSAGE_BY_KEY.get(key);
    }

    public static String format(KeyMessage templateKey, KeyMessage parameterKey) {
        return String.format(resolve(templateKey), resolve(parameterKey));
    }

    public static String format(KeyMessage templateKey, String value) {
        return String.format(resolve(templateKey), value);
    }
}
