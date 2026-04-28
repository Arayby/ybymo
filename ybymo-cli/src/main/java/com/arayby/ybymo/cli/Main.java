package com.arayby.ybymo.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "ybymo",
                     mixinStandardHelpOptions = true,
                     version = "1.0-SNAPSHOT",
                     description = "Extração, conversão e transformação de dados.")
public class Main implements Runnable {

    static void main(String[] args) {
        System.exit(new CommandLine(new Main()).execute(args));
    }

    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
