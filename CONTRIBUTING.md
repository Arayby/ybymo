# Guia de Contribuição do Ybymo

Este guia foi criado para tornar o processo de contribuição o mais claro e simples possível.

## Como Você Pode Ajudar

- 🐛 **Reportar Bugs**: Abra uma [Issue](https://github.com/arayby/ybymo/issues) detalhando o problema.
- 💡 **Sugerir Melhorias**: Inicie uma discussão em [Discussions](https://github.com/arayby/ybymo/discussions).
- 📄 **Melhorar a Documentação**: Envie um Pull Request com suas sugestões.
- 💻 **Escrever Código**: Implemente novas funcionalidades ou corrija bugs.

## Seu Primeiro Commit em 5 Passos

Quer fazer sua primeira contribuição? Siga estes passos:

1. **Crie uma branch**: `git checkout -b fix/corrige-erro-leia-me`
2. **Faça sua mudança**: Corrija um erro de digitação ou melhore uma frase na documentação.
3. **Atualize o CHANGELOG**: Adicione `- Correção de erro de digitação no README.md` na seção de snapshot ativo do `CHANGELOG.md` do módulo impactado:
    - Para core: [`ybymo-core/CHANGELOG.md`](ybymo-core/CHANGELOG.md)
    - Para cli: [`ybymo-cli/CHANGELOG.md`](ybymo-cli/CHANGELOG.md)
4. **Faça o commit**: `git commit -m "docs: corrige erro de digitação no README"`
5. **Abra um Pull Request** e aguarde a revisão!

## Fluxo de Desenvolvimento Detalhado

### 1. Configuração do Ambiente

- **Requisito**: GraalVM para Java 25.
- **Recomendação**: Use SDKMAN!.

Com o `SDKMAN!` instalado, basta rodar o comando abaixo na raiz do projeto:

```bash
sdk env install
```

Ele instalará e configurará a versão correta do Java automaticamente.

### 2. Ciclo de Desenvolvimento

1. **Crie uma branch**: Use um nome descritivo seguindo o padrão:
    - `feat/<escopo>-nome-da-funcionalidade` (ex.: `feat/core-extrator-json`)
    - `fix/<escopo>-descricao-do-problema` (ex.: `fix/cli-parsing-args`)
    - `docs/melhora-documentacao-x`
    - Escopos recomendados: `core`, `cli`, ou deixe em branco para mudanças gerais

2. **Implemente e teste**:
    - Escreva o código da sua funcionalidade ou correção.
    - **Adicione testes unitários** para validar seu código. A cobertura de testes é fundamental.
    - Rode os testes localmente: `./gradlew test`.

3. **Atualize o CHANGELOG apropriado**:
    - Este passo é **obrigatório**. Builds e Pull Requests falharão se o `CHANGELOG.md` não for modificado.
    - **Escolha o changelog certo**:
        - Se alterou **`ybymo-core`**: atualize apenas [`ybymo-core/CHANGELOG.md`](ybymo-core/CHANGELOG.md)
        - Se alterou **`ybymo-cli`**: atualize apenas [`ybymo-cli/CHANGELOG.md`](ybymo-cli/CHANGELOG.md)
    - Adicione uma nova entrada na seção de snapshot ativo (`[X.Y.Z-SNAPSHOT]`).
    - Use uma das categorias indicadas no arquivo.

   **Exemplo de mudança no core:**
   ```markdown
   # ybymo-core/CHANGELOG.md
   ## [1.2.0-SNAPSHOT] - DD/MM/AAAA

   #### Adicionado
   - Novo extrator para dados JSON
   ```

   **Exemplo de mudança no CLI:**
   ```markdown
   # ybymo-cli/CHANGELOG.md
   ## [1.3.0-SNAPSHOT] - DD/MM/AAAA

   #### Alterado
   - Melhorada validação de argumentos de entrada
   ```

4. **Faça o commit**:
    - Use o padrão Conventional Commits com escopo (`core`, `cli`, etc.).
    - **Exemplos**:
        - `feat(core): adiciona extrator de JSON`
        - `fix(cli): corrige parsing de argumentos`
        - `docs: melhora exemplos de uso`

5. **Valide tudo localmente**:
    - Antes de enviar, rode a build do projeto. Ele executa testes e valida o CHANGELOG.
    - `./gradlew build`
    - Para validar cobertura e análise SonarCloud, rode também `./gradlew test jacocoTestReport sonar`.

6. **Envie o Pull Request**:
    - Faça o push da sua branch para o GitHub e abra um Pull Request para a branch `main`.
    - Preencha o Pull Request com uma descrição clara de suas mudanças e qual(is) módulo(s) foi(foram) impactado(s).

### 3. Processo de Release

Este monorepo implementa **versionamento independente por módulo**. A release de `ybymo-core` é totalmente independente da release de `ybymo-cli`.

#### 3.1. Preparar Release de um Módulo

Escolha qual módulo será released (ou ambos):

1. **Prepare a versão de release**:
    - No CHANGELOG.md do módulo, crie uma seção `## [X.Y.Z] - DD/MM/AAAA` com as mudanças de release.
    - Mantenha uma seção de snapshot ativa no topo para o próximo ciclo (ex.: `## [X.Y.(Z+1)-SNAPSHOT]`).

   **Exemplo para core:**
   ```markdown
   # ybymo-core/CHANGELOG.md
   ## [1.2.0] - 17/05/2026
   
   ### Adicionado
   - Novo extrator para JSON
   
   ### Corrigido
   - Bug em validação de entrada
   
   ---
   
   ## [1.3.0-SNAPSHOT] - DD/MM/AAAA
   
   ### Adicionado
   - [Mudanças futuras aqui]
   ```

2. **Sincronize a versão**:
    - Atualize o `build.gradle` do módulo com a mesma versão de release.
    - Na raiz do projeto, rode validação do módulo:
   ```bash
   cd ybymo-core  # ou ybymo-cli
   ../gradlew validateChangelog validateVersionSync
   ```

3. **Abra um Pull Request para `main`**:
    - Inclua ambos os arquivos atualizados (`build.gradle` e `CHANGELOG.md` do módulo).
    - Exemplo de PR: `Release core v1.2.0`

4. **Merge**: Após aprovação e merge, o GitHub Actions irá automaticamente:
    - Detectar a mudança em `ybymo-core/CHANGELOG.md` ou `ybymo-cli/CHANGELOG.md`
    - Criar uma tag (`core-vX.Y.Z` ou `cli-vX.Y.Z`)
    - Disparar o workflow de release, publicando a versão no GitHub Releases

5. **Inicie o próximo ciclo**:
    - Atualize `build.gradle` do módulo para a próxima versão de desenvolvimento (ex.: `X.Y.(Z+1)-SNAPSHOT`).
    - Use a seção de snapshot ativa do `CHANGELOG.md` para registrar novas mudanças.

#### 3.2. Exemplo: Release Simultânea de Core e CLI

Se ambos os módulos têm mudanças para release:

```bash
# 1. Atualize ybymo-core/CHANGELOG.md (secção [1.2.0] - DD/MM/AAAA)
# 2. Atualize ybymo-core/build.gradle (version = '1.2.0')
# 3. Atualize ybymo-cli/CHANGELOG.md (secção [1.3.0] - DD/MM/AAAA)
# 4. Atualize ybymo-cli/build.gradle (version = '1.3.0')
# 5. Faça push e merge em main

# Resultado automático:
# - Tag: core-v1.2.0 → Release no GitHub com artefatos do core
# - Tag: cli-v1.3.0 → Release no GitHub com binários do CLI
```

#### 3.3. Aba de Releases no GitHub

Você verá na aba **Releases** do GitHub:

```
Releases
 cli-v1.3.0   ← Última versão do CLI
 core-v1.2.0  ← Última versão do core
 cli-v1.2.0   ← Release anterior do CLI
 core-v1.1.0  ← Release anterior do core
 ...
```

Cada release contém:

- **Artefatos específicos**: JARs para core, binários nativos para CLI
- **Notas de release**: Extraídas do `CHANGELOG.md` do módulo respectivo
- **Rastreabilidade completa**: Commits entre versões

Este fluxo garante que cada versão seja bem documentada e o processo, consistente.
