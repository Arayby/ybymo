package com.arayby.ybymo.cli;

import com.arayby.ybymo.cli.command.CsvCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "ybymo",
         mixinStandardHelpOptions = true,
         version = "0.1.0",
         description = "Ybymo — extração, conversão e transformação de dados.",
         subcommands = {CsvCommand.class})
public class Main implements Runnable {

    static void main(String[] args) {
        System.exit(new CommandLine(new Main()).execute(args));
    }

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }
}
