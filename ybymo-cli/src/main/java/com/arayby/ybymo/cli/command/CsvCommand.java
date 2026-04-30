package com.arayby.ybymo.cli.command;

import com.arayby.ybymo.core.extractor.csv.CsvExtractor;
import com.arayby.ybymo.core.model.DataRecord;
import com.arayby.ybymo.core.pipeline.Pipeline;
import com.arayby.ybymo.core.transformer.CleanTransformer;
import com.arayby.ybymo.core.transformer.PrefixTransformer;
import com.arayby.ybymo.core.transformer.SuffixTransformer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Command(name = "csv",
         mixinStandardHelpOptions = true,
         description = "Extrai e transforma colunas de um arquivo CSV e salva resultado em arquivo.")
public class CsvCommand implements Runnable {

    @Spec
    private CommandSpec spec;

    @Parameters(index = "0",
                description = "Caminho para o arquivo CSV de entrada.")
    private Path file;

    @Parameters(index = "1",
                description = "Índices das colunas a extrair, separados por vírgula (1-based). Ex: 1,3,4",
                split = ",")
    private List<Integer> columns;

    @Option(names = {"-H", "--skip-header"},
            description = "Ignora a primeira linha (cabeçalho) do arquivo CSV.")
    private boolean skipHeader;

    @Option(names = "-c",
            description = "Remove caracteres de formatação dos valores (aspas, parênteses, hifens, etc.).")
    private boolean clean;

    @Option(names = "-p",
            description = "Prefixo a adicionar em cada valor. Ex: -p \"@\"")
    private String prefix;

    @Option(names = "-s",
            description = "Sufixo a adicionar em cada valor. Ex: -s \".com\"")
    private String suffix;

    @Override
    public void run() {
        List<DataRecord> records;
        try {
            records = new CsvExtractor().extract(file, columns);
        } catch (IOException e) {
            spec.commandLine().getErr().println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        if (records.isEmpty()) {
            spec.commandLine().getErr().println("Nenhum registro encontrado no arquivo.");
            return;
        }

        DataRecord headerRecord = records.getFirst();
        List<DataRecord> data = records.size() > 1 ? records.subList(1, records.size()) : List.of();

        Pipeline pipeline = buildPipeline();
        List<DataRecord> transformedData = pipeline.execute(data);

        Path outputPath = buildOutputPath();

        try (FileWriter writer = new FileWriter(outputPath.toFile()); CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            if (!skipHeader) {
                String[] headerValues = headerRecord.fields().stream().map(DataRecord.Field::value).toArray(String[]::new);
                printer.printRecord((Object[]) headerValues);
            }

            for (DataRecord record : transformedData) {
                String[] values = record.fields().stream().map(DataRecord.Field::value).toArray(String[]::new);
                printer.printRecord((Object[]) values);
            }

            spec.commandLine().getOut().println("Arquivo salvo com sucesso: " + outputPath);

        } catch (IOException e) {
            spec.commandLine().getErr().println("Erro ao gravar o arquivo: " + e.getMessage());
        }
    }

    private Path buildOutputPath() {
        String fileName = file.getFileName().toString();
        int lastDot = fileName.lastIndexOf('.');
        String baseName;
        String extension;

        if (lastDot > 0) {
            baseName = fileName.substring(0, lastDot);
            extension = fileName.substring(lastDot);
        } else {
            baseName = fileName;
            extension = "";
        }

        String outputFileName = baseName + "_ybymo" + extension;
        Path parentDir = file.getParent() != null ? file.getParent() : Paths.get(".");
        return parentDir.resolve(outputFileName);
    }

    private Pipeline buildPipeline() {
        Pipeline.Builder builder = Pipeline.builder();
        if (clean) builder.add(new CleanTransformer());
        if (prefix != null) builder.add(new PrefixTransformer(prefix));
        if (suffix != null) builder.add(new SuffixTransformer(suffix));
        return builder.build();
    }
}
