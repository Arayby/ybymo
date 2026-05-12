# Ybymo

> Extração, conversão e transformação de dados — um produto [Arayby](https://github.com/arayby).

Ybymo é uma ferramenta de código aberto para extração e transformação de dados estruturados.
Fornece uma arquitetura modular e extensível para extrair, transformar e processar dados via CLI com máximo desempenho.

## Características

- ✅ **Múltiplos Formatos**: Extração de dados em CSV e JSON (WIP).
- ✅ **Interface CLI**: Interação de forma simples e direta no terminal.
- ✅ **Executável Nativo**: Desempenho otimizado por compilação nativa com GraalVM.
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
Use o `SDKMAN!` para instalar e configurar a versão correta do Java automaticamente.
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
3.  **Atualize o CHANGELOG**: Adicione uma entrada na seção de snapshot ativo (`[X.Y.Z-SNAPSHOT]`) do arquivo `CHANGELOG.md` do módulo impactado:
    - [`ybymo-core/CHANGELOG.md`](ybymo-core/CHANGELOG.md) para mudanças no core
    - [`ybymo-cli/CHANGELOG.md`](ybymo-cli/CHANGELOG.md) para mudanças no CLI
4.  **Valide localmente**: Rode `./gradlew build`.
5.  **Envie um Pull Request**.

Para um guia detalhado sobre o fluxo de desenvolvimento, versionamento, e padrões de commit, leia [CONTRIBUTING.md](CONTRIBUTING.md).

## Qualidade e SonarCloud

O projeto usa SonarCloud com decoração automática de Pull Request e análise no branch `main`.

- O workflow do GitHub Actions está em [`.github/workflows/sonar.yml`](.github/workflows/sonarcloud.yml).
- Cada módulo gera seu próprio relatório de cobertura JaCoCo em XML.
- A análise pode ser executada localmente com:

```bash
./gradlew test jacocoTestReport sonar
```

Para o CI, configure os secrets e variáveis no GitHub:

- `SONAR_TOKEN` em *Secrets*
- `SONAR_ORGANIZATION` em *Variables* quando quiser sobrescrever o valor padrão
- `SONAR_PROJECT_KEY` em *Variables* quando quiser sobrescrever o valor padrão

## Versionamento e Releases

Este monorepo utiliza **versionamento independente por módulo**, permitindo ciclos de release diferentes para `ybymo-core` e `ybymo-cli`.

### Estrutura de Versionamento

- **`ybymo-core`**: Biblioteca principal de extração e transformação (versão própria, tags `core-vX.Y.Z`)
- **`ybymo-cli`**: Interface CLI que consome o core (versão própria, tags `cli-vX.Y.Z`)

### Padrões Adotados

- **SemVer**: Cada módulo segue [Semantic Versioning](https://semver.org/lang/pt-BR/2.0.0/).
- **Changelog por Módulo**: 
  - [`ybymo-core/CHANGELOG.md`](ybymo-core/CHANGELOG.md) — histórico de mudanças do core
  - [`ybymo-cli/CHANGELOG.md`](ybymo-cli/CHANGELOG.md) — histórico de mudanças do CLI
- **Tags e Releases**: Cada tag dispara uma release independente no GitHub com artefatos específicos.
- **Compatibilidade**: O CLI declara qual(is) faixa(s) de versão do core é compatível no `build.gradle`.

### Fluxo de Release

1. Altere apenas o(s) módulo(s) impactado(s).
2. Atualize a seção de snapshot no `CHANGELOG.md` do módulo (ex.: `[1.2.0-SNAPSHOT]`).
3. Sincronize a versão em `build.gradle` do módulo com a snapshot ativa.
4. Faça merge em `main` — o workflow `tag.yml` cria automaticamente as tags necessárias.
5. O workflow `release.yml` publica a release no GitHub com os artefatos corretos.

### Exemplo: Mudanças em Ambos os Módulos

```bash
# Atualize ybymo-core/CHANGELOG.md (nova versão 1.3.0)
# Atualize ybymo-core/build.gradle (version = '1.3.0')
# Atualize ybymo-cli/CHANGELOG.md (nova versão 1.3.0)
# Atualize ybymo-cli/build.gradle (version = '1.3.0')

# Merge em main
git push origin main

# Resultado: tags criadas automaticamente
# - core-v1.3.0 → release do core
# - cli-v1.3.0 → release do CLI
```

## Licença

Este projeto está licenciado sob a licença [**MIT**](LICENSE). Consulte o arquivo LICENSE para detalhes.
