# Guia de Contribuição do Ybymo

Este guia foi criado para tornar o processo de contribuição o mais claro e simples possível.

## Como Você Pode Ajudar

-   🐛 **Reportar Bugs**: Abra uma [Issue](https://github.com/arayby/ybymo/issues) detalhando o problema.
-   💡 **Sugerir Melhorias**: Inicie uma discussão em [Discussions](https://github.com/arayby/ybymo/discussions).
-   📄 **Melhorar a Documentação**: Envie um Pull Request com suas sugestões.
-   💻 **Escrever Código**: Implemente novas funcionalidades ou corrija bugs.

## Seu Primeiro Commit em 5 Passos

Quer fazer sua primeira contribuição? Siga estes passos:

1.  **Crie uma branch**: `git checkout -b fix/corrige-erro-leia-me`
2.  **Faça sua mudança**: Corrija um erro de digitação ou melhore uma frase na documentação.
3.  **Atualize o CHANGELOG**: Adicione `- Correção de erro de digitação no README.md` na seção `[Planejado]` do `CHANGELOG.md`.
4.  **Faça o commit**: `git commit -m "docs: corrige erro de digitação no README"`
5.  **Abra um Pull Request** e aguarde a revisão!

## Fluxo de Desenvolvimento Detalhado

### 1. Configuração do Ambiente

-   **Requisito**: GraalVM para Java 25.
-   **Recomendação**: Use SDKMAN!.

Com o SDKMAN! instalado, basta rodar o comando abaixo na raiz do projeto:
```bash
sdk env install
```
Ele instalará e configurará a versão correta do Java automaticamente.

### 2. Ciclo de Desenvolvimento

1.  **Crie uma branch**: Use um nome descritivo seguindo o padrão:
    -   `feat/nome-da-funcionalidade`
    -   `fix/descricao-do-problema`
    -   `docs/melhora-documentacao-x`

2.  **Implemente e teste**:
    -   Escreva o código da sua funcionalidade ou correção.
    -   **Adicione testes unitários** para validar seu código. A cobertura de testes é fundamental.
    -   Rode os testes localmente: `./gradlew test`.

3.  **Atualize o CHANGELOG.md**:
    -   Este passo é **obrigatório**. Builds e Pull Requests falharão se o `CHANGELOG.md` não for modificado.
    -   Adicione uma nova entrada na seção `[Planejado]`.
    -   Use uma das categorias indicadas no `CHANGELOG.md`.

    **Exemplo:**
    ```markdown
    ### [Planejado]

    #### Adicionado
    - Nova funcionalidade de exportação para JSON.
    ```

4.  **Faça o commit**:
    -   Use o padrão Conventional Commits.
    -   **Exemplos**:
        -   `feat(extractor): adiciona extrator de JSON`
        -   `fix(cli): corrige parsing de argumentos`
        -   `docs(readme): melhora exemplos de uso`

5.  **Valide tudo localmente**:
    -   Antes de enviar, rode a build do projeto. Ele executa testes e valida o CHANGELOG.
    -   `./gradlew build`

6.  **Envie o Pull Request**:
    -   Faça o push da sua branch para o GitHub e abra um Pull Request para a branch `main`.
    -   Preencha o Pull Request com uma descrição clara de suas mudanças.

### 3. Processo de Release

O processo de release é automatizado:

1.  **Crie uma release branch**: `release/vX.Y.Z`.
2.  **Atualize o CHANGELOG**: Mova o conteúdo de `[Planejado]` para uma nova versão, ex: `[1.1.0] - DD/MM/YYYY`.
3.  **Sincronize a versão**: Rode `./gradlew validateVersionSync` para garantir que a versão do `build.gradle` e `CHANGELOG.md` estão alinhadas.
4.  **Abra um Pull Request**: Envie o Pull Request da release branch para a `main`.
5.  **Merge**: Após a aprovação e merge, o GitHub Actions irá:
    -   Criar uma tag Git (`vX.Y.Z`).
    -   Gerar os artefatos da release.
    -   Publicar a nova versão no GitHub Releases.

Este fluxo garante que cada versão seja bem documentada e o processo, consistente.
