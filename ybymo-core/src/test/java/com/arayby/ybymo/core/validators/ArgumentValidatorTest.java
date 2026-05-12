package com.arayby.ybymo.core.validators;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArgumentValidatorTest {

    @Test
    void constructor_whenCalledViaReflection_throwsInvocationTargetException() throws Exception {
        Constructor<ArgumentValidator> constructor = ArgumentValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class).hasCauseInstanceOf(AssertionError.class);
    }
}
