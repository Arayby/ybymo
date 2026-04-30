package com.arayby.ybymo.cli;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MainTest {

    @Test
    void run_whenNoArgs_printsUsage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            new Main().run();

            assertThat(out.toString()).contains("ybymo");
            assertThat(out.toString()).contains("csv");
        } finally {
            System.setOut(originalOut);
        }
    }
}
