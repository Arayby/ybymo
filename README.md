# Ybymo

> Extração, conversão e transformação de dados — um produto [Arayby](https://github.com/arayby).

Ybymo é uma ferramenta de código aberto para extração e transformação de dados estruturados.
Fornece uma arquitetura modular e extensível para extrair, transformar e processar dados via CLI com máxima performance.

## Características

- ✅ **Múltiplos Formatos**: Extração de dados em CSV e JSON (WIP).
- ✅ **Interface CLI**: Interação de forma simples e direta no terminal.
- ✅ **Executável Nativo**: Performance otimizada por compilação nativa com GraalVM.
- ✅ **Validação Automática**: Processo de contribuição com validação de CHANGELOG.

## Exemplos de Uso

```bash
# Ver opções disponíveis
ybymo --help

# Processar colunas 1 e 3 de um arquivo CSV, removendo caracteres especiais
ybymo csv input.csv 1,3 -c

# Aplicar prefixo "XYZ_" sufixo "_DATA" à coluna 2
ybymo csv input.csv 2 -p "XYZ_" -s "_DATA"
```

## Começando

**Requisitos:**
- GraalVM 25 (`java=25.0.3-graal`)
- SDKMAN! (recomendado)

**1. Configure o Ambiente:**
Use o SDKMAN! para instalar e configurar a versão correta do Java automaticamente.
```bash
# Instala e configura o ambiente Java com base no arquivo .sdkmanrc
sdk env install
```

**2. Comandos Essenciais:**
```bash
# Rodar todos os testes
./gradlew test

# Compilar o projeto e validar o CHANGELOG
./gradlew build

# Gerar o executável nativo (em ybymo-cli/build/native/nativeCompile/)
./gradlew :ybymo-cli:nativeCompile
```

## Como Contribuir

Contribuições são bem-vindas! O fluxo é simples:

1.  **Crie uma branch**: `git checkout -b feat/minha-feature`.
2.  **Desenvolva**: Adicione seu código e testes.
3.  **Atualize o CHANGELOG**: Adicione uma entrada na seção de snapshot ativo (`[X.Y.Z-SNAPSHOT]`) do [CHANGELOG.md](CHANGELOG.md).
4.  **Valide localmente**: Rode `./gradlew build`.
5.  **Envie um Pull Request**.

Para um guia detalhado sobre o fluxo de desenvolvimento, versionamento, e padrões de commit, leia [CONTRIBUTING.md](CONTRIBUTING.md).

## Versionamento e Releases

- **Versionamento**: Utilização de Semantic Versioning.
- **Histórico de Mudanças**: Alterações são documentadas no CHANGELOG.md.
- **Processo de Release**: Merge na `main` com `CHANGELOG.md`/`build.gradle` em estado de release cria a tag automaticamente; a publicação da release com binários Linux/macOS/Windows ocorre no workflow de release.

## Licença

Este projeto está licenciado sob a licença [**MIT**](LICENSE). Consulte o arquivo LICENSE para detalhes.
