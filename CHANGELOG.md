# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/) e este projeto adere ao [Semantic Versioning](https://semver.org/lang/pt-BR/).

## [Planejado]

### Adicionado
- Estrutura inicial do projeto: módulos `ybymo-core` e `ybymo-cli`, configuração Gradle multi-módulo e GraalVM Native Image
- Módulo `ybymo-core` com extração e transformação de dados estruturados
- Módulo `ybymo-cli` com interface de linha de comando usando PicoCLI
- Suporte completo para extração de dados em formato CSV
- Pipeline de transformação de dados configurável e extensível
- Transformadores: `CleanTransformer`, `PrefixTransformer`, `SuffixTransformer`
- Suite de testes com JUnit 5 e AssertJ seguindo padrão AAA
- Compilação para binário nativo com GraalVM Native Image
- Suporte para visualização estruturada de dados (`DataRecord`)
- Documentação de contribuição (`CONTRIBUTING.md`) com padrão Conventional Commits
- Sistema de CHANGELOG com validação obrigatória em cada contribuição
- Validação automática via Gradle task `validateChangelog`
- Git hook pré-commit para lembrete de atualização do CHANGELOG

### Alterado

### Corrigido

### Descontinuado

### Removido

### Segurança
- Validação de entrada em todos os módulos core e CLI
- Tratamento seguro de recursos com try-with-resources

---

[Planejado]: https://github.com/arayby/ybymo/compare/a34bbbf8b36f74ea82867717a5e41ceee5403a92...HEAD
