package com.arayby.ybymo.cli.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.arayby.ybymo.cli.builders.CsvTestFileBuilder.csvIn;
import static org.assertj.core.api.Assertions.assertThat;

class CsvCommandTest {

    private final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
    private final ByteArrayOutputStream stderr = new ByteArrayOutputStream();

    @TempDir
    Path tempDir;

    private PrintStream originalOut;
    private PrintStream originalErr;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalErr = System.err;
        System.setOut(new PrintStream(stdout));
        System.setErr(new PrintStream(stderr));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void run_whenValidCsvWithHeaderAndTransforms_writesTransformedFile() throws IOException {
        Path input = csvIn(tempDir).row("name", "email").row("Alice", " @2.345-2").build();

        int exitCode = new CommandLine(new CsvCommand()).execute(input.toString(), "2", "-c", "-p", "@", "-s", ".com");
        Path output = tempDir.resolve("input_ybymo.csv");
        List<String> lines = Files.readAllLines(output);

        assertThat(exitCode).isZero();
        assertThat(lines).containsExactly("email", "@23452.com");
        assertThat(stderr.toString()).isEmpty();
    }

    @Test
    void run_whenSkipHeaderTrue_doesNotWriteHeader() throws IOException {
        Path input = csvIn(tempDir).row("name", "city").row("Alice", "London").build();

        new CommandLine(new CsvCommand()).execute(input.toString(), "1", "-H");
        Path output = tempDir.resolve("input_ybymo.csv");
        List<String> lines = Files.readAllLines(output);

        assertThat(lines).containsExactly("Alice");
    }

    @Test
    void run_whenInputHasNoExtension_writesOutputWithSuffixOnly() throws IOException {
        Path input = tempDir.resolve("input");
        Files.writeString(input, "value\nAlice\n");

        new CommandLine(new CsvCommand()).execute(input.toString(), "1", "-H");
        Path output = tempDir.resolve("input_ybymo");

        assertThat(output).exists();
    }

    @Test
    void run_whenCsvEmpty_printsNoRecordsMessageAndDoesNotWriteOutput() throws IOException {
        Path input = csvIn(tempDir).build();

        new CommandLine(new CsvCommand()).execute(input.toString(), "1");
        Path output = tempDir.resolve("input_ybymo.csv");

        assertThat(output).doesNotExist();
        assertThat(stderr.toString()).contains("Nenhum registro encontrado no arquivo.");
    }

    @Test
    void run_whenFileMissing_printsReadErrorAndDoesNotWriteOutput() {
        Path input = tempDir.resolve("missing.csv");

        new CommandLine(new CsvCommand()).execute(input.toString(), "1");
        Path output = tempDir.resolve("missing_ybymo.csv");

        assertThat(output).doesNotExist();
        assertThat(stderr.toString()).contains("Erro ao ler o arquivo:");
    }

    @Test
    void run_whenOutputPathIsDirectory_printsWriteError() throws IOException {
        Path input = csvIn(tempDir).row("name").row("Alice").build();
        Path outputDir = tempDir.resolve("input_ybymo.csv");
        Files.createDirectory(outputDir);
        new CommandLine(new CsvCommand()).execute(input.toString(), "1", "-H");

        assertThat(stderr.toString()).contains("Erro ao gravar o arquivo:");
    }

    @Test
    void run_whenOnlyHeaderRecord_writesHeaderOnly() throws IOException {
        Path input = csvIn(tempDir).row("name", "email").build();
        new CommandLine(new CsvCommand()).execute(input.toString(), "2");
        Path output = tempDir.resolve("input_ybymo.csv");
        List<String> lines = Files.readAllLines(output);

        assertThat(lines).containsExactly("email");
    }

    @Test
    void run_whenRelativePathNoParent_writesOutputInCurrentDirectory() throws Exception {
        CsvCommand command = new CsvCommand();
        var fileField = CsvCommand.class.getDeclaredField("file");
        fileField.setAccessible(true);
        fileField.set(command, Path.of("input.csv"));

        var method = CsvCommand.class.getDeclaredMethod("buildOutputPath");
        method.setAccessible(true);
        Path output = (Path) method.invoke(command);

        assertThat(output).isEqualTo(Path.of(".").resolve("input_ybymo.csv"));
    }
}
