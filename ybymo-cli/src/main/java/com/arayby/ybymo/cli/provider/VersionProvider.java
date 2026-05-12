package com.arayby.ybymo.cli.provider;

import picocli.CommandLine.IVersionProvider;

public class VersionProvider implements IVersionProvider {

    protected String getPackageImplementationVersion() {
        return VersionProvider.class.getPackage().getImplementationVersion();
    }

    protected String getImplementationVersion() {
        String version = getPackageImplementationVersion();
        return version != null ? version : "unknown";
    }

    @Override
    public String[] getVersion() {
        String version = getImplementationVersion();
        return new String[]{"ybymo " + version};
    }
}
