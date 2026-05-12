package com.arayby.ybymo.build.messages;

public final class BuildMessages {

    public static final String BUILD_FILE_NOT_FOUND = "build.gradle não encontrado";
    public static final String CHANGELOG_MUST_START_WITH_SNAPSHOT = "CHANGELOG.md precisa iniciar com uma seção de desenvolvimento no formato ## [X.Y.Z-SNAPSHOT]";
    public static final String CHANGELOG_NOT_FOUND = "CHANGELOG.md não encontrado na raiz do projeto";
    public static final String CHANGELOG_VALIDATED = "CHANGELOG.md validado com sucesso (%s)";
    public static final String COULD_NOT_CREATE_OUTPUT_DIR = "Não foi possível criar o diretório de saída";
    public static final String RELEASE_NOTES_EXTRACTED = "Notas de release extraídas com sucesso";
    public static final String RELEASE_NOTES_NOT_FOUND = "Nenhuma nota de release encontrada no CHANGELOG.md";
    public static final String RELEASE_VERSION_NOT_FOUND = "Nenhuma versão de release encontrada no CHANGELOG.md. Formato esperado: ## [X.Y.Z]";
    public static final String SNAPSHOT_PLACEHOLDER_SYNC = "Versionamento sincronizado: CHANGELOG=[%s] (placeholder ativo)";
    public static final String SNAPSHOT_SECTION_NOT_FOUND = "Nenhuma seção SNAPSHOT encontrada no CHANGELOG.md. Formato esperado: ## [X.Y.Z-SNAPSHOT]";
    public static final String VERSIONS_MISMATCH = "Versões desalinhadas:\nCHANGELOG: [%s]\nbuild.gradle: %s\n\ninfo: build.gradle deve estar exatamente igual a seção SNAPSHOT ativa do CHANGELOG.";
    public static final String VERSION_EXTRACTED = "Versão extraída do CHANGELOG: %s";
    public static final String VERSION_NOT_FOUND = "Versão nao encontrada no build.gradle";
    public static final String VERSION_SYNC_OK = "Versionamento sincronizado: CHANGELOG=[%s], build.gradle=%s";
    public static final String SONAR_PROPERTIES_CONFIG_FAILED = "Falha ao configurar propriedades do SonarCloud";
    public static final String SONAR_PROPERTY_SET_FAILED = "Falha ao definir propriedade do SonarCloud: %s";

    private BuildMessages() {
    }
}
