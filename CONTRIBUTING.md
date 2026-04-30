# Contribuindo para o Ybymo

Obrigado por considerar contribuir para o Ybymo! Este documento fornece orientações e instruções para tornar o processo de contribuição claro e eficiente.

## Código de Conduta

Esperamos que todos os contribuidores sigam um padrão de comportamento respeitoso e inclusivo. Qualquer violação será tratada com seriedade.

## Como Contribuir

### 1. Prepare seu Ambiente

**Requisitos:**
- GraalVM 25 (`java=25.0.3-graal`)
- [SDKMAN!](https://sdkman.io) (opcional, recomendado)

**Instalação:**
```bash
# Com SDKMAN!
sdk env install

# Sem SDKMAN! - instale GraalVM 25 manualmente
```

**Validação:**
```bash
java -version
# Deve exibir: GraalVM 25.x.x
```

### 2. Fork e Clone

```bash
git clone git@github.com:Arayby/ybymo.git
cd ybymo
```

### 3. Crie uma Branch

Use nomes descritivos para branches:

```bash
git checkout -b feat/new-feature-name
git checkout -b fix/bug-description
git checkout -b docs/update-readme
```

### 4. Desenvolva e Teste

```bash
# Executar testes
./gradlew test

# Compilar projeto
./gradlew build

# Gerar binário nativo (opcional)
./gradlew :ybymo-cli:nativeCompile
```

### 5. Atualize o CHANGELOG.md

Edite o arquivo `CHANGELOG.md` na seção `[Planejado]` e adicione suas mudanças na categoria apropriada:

#### Categorias do CHANGELOG

- **Adicionado**: Novas funcionalidades ou extractors/transformers
- **Alterado**: Alterações em funcionalidades existentes, mudanças de API
- **Corrigido**: Correções de bugs
- **Descontinuado**: Funcionalidades marcadas para remoção futura
- **Removido**: Remoção de funcionalidades/módulos
- **Segurança**: Patches de segurança e melhorias

#### Exemplo de Entrada

```markdown
## [Planejado]

### Adicionado
- Novo `JsonExtractor` para leitura de arquivos JSON
- Suporte para custom encoding em `CsvExtractor`

### Corrigido
- Bug: `PrefixTransformer` lançava `NullPointerException` com valores null

### Alterado
- Performance do `CleanTransformer` melhorada em 25%
```

**Importante**: Se o `[Planejado]` estiver vazio e você submeter um PR, a build **falhará**. Isto é intencional para garantir que toda contribuição seja documentada.

### 6. Commit com Padrão Conventional Commits

Mantenha mensagens de commit estruturadas para facilitar o versionamento semântico:

```bash
git commit -m "feat(csv-extractor): add custom encoding support"
git commit -m "fix(pipeline): prevent memory leak in stream processing"
git commit -m "docs(changelog): update version 1.1.0"
git commit -m "test(data-record): add edge case tests"
```

**Formato**: `type(scope): description`

**Tipos Comuns:**
- `feat`: Uma nova funcionalidade
- `fix`: Uma correção de bug
- `docs`: Alteração apenas em documentação
- `test`: Adição ou alteração de testes
- `refactor`: Refatoração sem mudança de funcionalidade
- `perf`: Melhoria de performance
- `chore`: Alterações de build, dependencies, etc

### 7. Push e Abra um Pull Request

```bash
git push origin feat/new-feature-name
```

No GitHub, abra um Pull Request descrevendo:
- O que foi alterado
- Por que foi alterado
- Como testar as alterações

**O PR será rejeitado se:**
- Testes não passarem
- CHANGELOG.md não foi atualizado
- Cobertura de testes for insuficiente

## Padrões de Código

### Testes

Seguimos o padrão AAA (Arrange-Act-Assert) com JUnit 5 e AssertJ:

```java
@Test
void methodName_scenario_expectedResult() {
    // Arrange
    int value = 10;
    MyClass instance = new MyClass();
    
    // Act
    int result = instance.calculate(value);
    
    // Assert
    assertThat(result).isEqualTo(20);
}
```

**Cobertura Esperada:**
- Casos de sucesso
- Valores nulos, vazios, limites)
- Exceções esperadas

### Estilo de Código

- Encoding: UTF-8
- Indentação: 4 espaços
- Java Version: 25 (com preview features habilitadas)

## Estrutura do Projeto

```
ybymo/
├── ybymo-core/          # Lógica de core (extractors, transformers, pipeline)
├── ybymo-cli/           # Interface de linha de comando
├── CHANGELOG.md         # Histórico de mudanças
└── CONTRIBUTING.md      # Este guia
```

## Adicionando Novos Módulos/Features

Se estiver adicionando um novo extrator ou transformador:

1. Implemente em `ybymo-core`
2. Crie testes abrangentes (100% cobertura)
3. Exponha na CLI em `ybymo-cli` se relevante
4. Atualize `CHANGELOG.md` na seção `[Planejado]` → `Adicionado`

## Validação Local

Antes de fazer push, valide localmente:

```bash
# Executar todos os testes
./gradlew test

# Validar CHANGELOG (isso é feito automaticamente no CI)
# Apenas certifique-se que [Planejado] tem conteúdo relacionado às mudanças

# Build completo
./gradlew build
```

## CI/CD

Todo PR é automaticamente validado:

1. **Testes**: Suite completa de testes com JUnit 5
2. **CHANGELOG**: Validação obrigatória de atualização
3. **Build**: Compilação e geração de artefatos

Se alguma etapa falhar, o PR será bloqueado. Corrija os problemas e faça push novamente.

## Versioning e Releases

O projeto usa **Semantic Versioning (SemVer)**:
- `MAJOR.MINOR.PATCH` (ex: `1.0.0`, `1.1.0`, `2.0.0`)

**Como Funciona:**
- Tags Git com padrão `v*` disparam o workflow de release
- Release notes são geradas automaticamente do CHANGELOG.md
- Binários nativos para Linux, macOS e Windows são publicados

## Perguntas?

- Abra uma issue para discussão
- Verifique issues existentes antes de criar uma nova
- Para sugestões maiores, discuta em uma issue antes de desenvolver

---

**Obrigado por contribuir para o Ybymo!** 🎉

