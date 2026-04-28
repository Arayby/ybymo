# Ybymo

> Extração, conversão e transformação de dados — um produto [Arayby](https://github.com/arayby).

## O que é

Ybymo é uma ferramenta para extração e transformação de dados estruturados.

## Desenvolvimento

**Requisitos:** GraalVM 25 (`java=25.0.3-graal`)

O projeto inclui `.sdkmanrc` — com [SDKMAN!](https://sdkman.io) instalado, basta rodar:

```bash
sdk env install
```

```bash
# Rodar testes
./gradlew test

# Gerar fat JAR
./gradlew :ybymo-cli:fatJar

# Gerar binário nativo (requer GraalVM com native-image)
./gradlew :ybymo-cli:nativeCompile
```

## Estrutura

```
ybymo/
├── ybymo-core/   # Extração, transformação, pipeline
└── ybymo-cli/    # Interface de linha de comando
```

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
