package com.arayby.ybymo.core.messages;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CatalogMessageTest {

    @Test
    void constructor_whenCalledViaReflection_throwsInvocationTargetException() throws Exception {
        Constructor<CatalogMessage> constructor = CatalogMessage.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class).hasCauseInstanceOf(AssertionError.class);
    }

    @Test
    void resolve_allKeys_hasMapping() {
        KeyMessage[] keys = KeyMessage.values();

        KeyMessage[] missingKeys = Arrays.stream(keys).filter(key -> CatalogMessage.resolve(key) == null).toArray(KeyMessage[]::new);

        assertThat(missingKeys).as("Keys sem mapeamento no CatalogMessage").isEmpty();
    }

    @Test
    void resolve_allKeys_hasNonBlankMessage() {
        KeyMessage[] keys = KeyMessage.values();

        KeyMessage[] blankKeys = Arrays.stream(keys).filter(key -> {
            String value = CatalogMessage.resolve(key);
            return value == null || value.isBlank();
        }).toArray(KeyMessage[]::new);

        assertThat(blankKeys).as("Keys com mensagem nula ou em branco").isEmpty();
    }
}
