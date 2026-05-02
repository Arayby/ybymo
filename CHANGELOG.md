# Changelog


> IMPORTANTE: Antes de fazer um commit, adicione suas mudanças na seção de snapshot ativo (`[X.Y.Z-SNAPSHOT]`).
> 
> Obs.: _A build falhará se este arquivo não for atualizado._

Use as seguintes categorias:
- Adicionado: para novas funcionalidades.
- Alterado: para mudanças em funcionalidades existentes.
- Corrigido: para correções de bugs.
- Descontinuado: para funcionalidades obsoletas.
- Removido: para funcionalidades removidas.
- Segurança: para correções de vulnerabilidades.

Todas as mudanças neste projeto serão documentadas neste arquivo.
O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/) e o projeto adere ao [Semantic Versioning](https://semver.org/lang/pt-BR/2.0.0/).

## [X.Y.Z-SNAPSHOT] - DD/MM/AAAA

---

## [1.1.2] - 02/05/2026

### Adicionado
- Workflow `ci.yml` agora executa validação explícita de sincronização de versão com `./gradlew validateVersionSync`.
- Workflow `ci.yml` agora gera binários nativos para Linux/macOS/Windows em ambiente de desenvolvimento e publica como artifacts do workflow.

### Alterado
- Padrão do changelog atualizado de `[Planejado]` para seção de snapshot versionada no formato `## [X.Y.Z-SNAPSHOT] - DD/MM/AAAA`.
- Workflow `tag.yml` simplificado para validar sincronização, extrair versão de release via `build/version.txt` e criar/push da tag sem commit automático na `main`.
- Workflow `release.yml` ajustado para publicar notas da release via `body_path` usando o arquivo gerado pela task `extractReleaseNotes`.
- Task `validateVersionSync` fortalecida para exigir igualdade exata entre versão do `build.gradle` e seção snapshot ativa do `CHANGELOG.md`.
- Fluxo de CI fortalecido para validar build nativo multi-OS antes do merge em `main`.

### Corrigido
- Removido comportamento de push forçado na `main` durante o processo de tag.
- Removida exigência artificial de entrada obrigatória na seção snapshot ativa durante a abertura de novo ciclo.

### Segurança
- Fluxo de tag endurecido para não recriar tags existentes.

---

## [1.1.1] - 02/05/2026

### Adicionado
- Workflow `release.yml` agora possui disparo manual `workflow_dispatch`.
- Workflow `tag.yml` para geração de tag baseada em CHANGELOG sem comitar diretamente na `main` protegida.

### Alterado
- Task de Gradle `validateVersionSync` reajustada para não checar se a versão em `build.gradle` inclui o `SNAPSHOT` antes da release.
- Documentação CONTRIBUTING.md atualizada com novo fluxo de release manual de tags para se adequar a ambiente GitHub restrito.

---

## [1.1.0] - 01/05/2026

### Adicionado
- Novo workflow: `tag.yml` para geração de tag baseada em CHANGELOG
- GitHub Environments: `production` (main) e `development` (branches) para isolamento de CI/CD
- Gradle task: `extractVersion` para parsing determinístico de versão do CHANGELOG
- Documentação completa de implementação e configuração de ambientes no GitHub
- Documentação CONTRIBUTING.md atualizada com novo fluxo de release automática

### Alterado
- Workflow `ci.yml`: agora suporta multi-branch (main + development) com ambientes dinâmicos e permissão de leitura
- Workflow `release.yml`: adicionado `environment: production` para releases, permissão de leitura de conteúdo, e ajustes na geração de artefatos compactados (`.tar.gz` para Linux/macOS e `.zip` para Windows)
- Otimização do fluxo de release para não gerar notas de release no GitHub automaticamente pelo `softprops/action-gh-release`, passando a usar a extração via Gradle
- Separação de tasks do Gradle para um arquivo externo (`gradle/tasks.gradle`), movendo a task `validateChangelog`
- Dependência adicionada para `validateVersionSync` no build do projeto
- Melhoria na estrutura e organização dos arquivos `README.md` e `CONTRIBUTING.md` para maior clareza e acolhimento a novos contribuidores.
- Adicionadas categorias padrão de documentação e aviso de quebra de build no cabeçalho do `CHANGELOG.md`.

### Segurança
- Validação de geração de tag implementado no workflow
- Validação determinística de versão com regex estrita no CHANGELOG

---

## [1.0.0] - 30/04/2026

### Adicionado
- Estrutura inicial do projeto: módulos `ybymo-core` e `ybymo-cli`, configuração Gradle multi-módulo e GraalVM Native Image
- Módulo `ybymo-core` com extração e transformação de dados estruturados
- Módulo `ybymo-cli` com interface de linha de comando usando PicoCLI
- Suporte completo para extração de dados em formato CSV
- Pipeline de transformação de dados configurável e extensível
- Transformadores: `CleanTransformer`, `PrefixTransformer`, `SuffixTransformer`
- Suíte de testes com JUnit 5 e AssertJ seguindo padrão AAA
- Compilação para binário nativo com GraalVM Native Image
- Suporte para visualização estruturada de dados (`DataRecord`)
- Documentação de contribuição (`CONTRIBUTING.md`) com padrão Conventional Commits
- Sistema de CHANGELOG com validação obrigatória em cada contribuição
- Validação automática via Gradle task `validateChangelog`
- Git hook pré-commit para lembrete de atualização do CHANGELOG

### Segurança
- Validação de entrada em todos os módulos core e CLI
- Tratamento seguro de recursos com try-with-resources

---

[X.Y.Z-SNAPSHOT]: https://github.com/arayby/ybymo/compare/v1.1.2...HEAD
[1.1.2]: https://github.com/arayby/ybymo/releases/tag/v1.1.2
[1.1.1]: https://github.com/arayby/ybymo/releases/tag/v1.1.1
[1.1.0]: https://github.com/arayby/ybymo/releases/tag/v1.1.0
[1.0.0]: https://github.com/arayby/ybymo/releases/tag/v1.0.0
