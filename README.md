# Sistema Bancário Digital

Backend de um banco digital desenvolvido em **Java 21** com **Spring Boot**, aplicando **Arquitetura Hexagonal (Ports & Adapters)** e boas práticas de desenvolvimento profissional.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue)
![Flyway](https://img.shields.io/badge/Flyway-migrations-red)

## Sobre o projeto

Projeto de portfólio que implementa o núcleo de um banco digital. O foco não é apenas "funcionar", mas demonstrar **arquitetura limpa, separação de responsabilidades e decisões técnicas conscientes** — o tipo de código que se mantém e evolui bem num ambiente real.

Cada decisão de arquitetura foi tomada de forma intencional, priorizando a testabilidade e a independência do núcleo de negócio em relação a frameworks e infraestrutura.

## Arquitetura

O projeto segue a **Arquitetura Hexagonal**: o domínio (regras de negócio) fica isolado no centro, sem depender de banco de dados, frameworks ou detalhes externos. A comunicação acontece por **portas** (interfaces) e **adaptadores** (implementações).

```
com.bancodigital
├── domain                          → o coração: regras de negócio em Java puro
│   ├── model                       → entidades de domínio (Cliente, Conta...)
│   ├── exception                   → exceções de negócio
│   └── port/out                    → contratos que o domínio define
├── application                     → casos de uso (orquestração)
└── infrastructure
    └── adapter/out/persistence     → entidades JPA, repositórios, mappers e adapters
```

**Por que isso importa:** trocar o PostgreSQL por outro banco, ou adicionar uma nova interface (API REST, CLI), não exige alterar uma única linha do domínio.

## Tecnologias

- **Java 21**
- **Spring Boot 4**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Flyway** — versionamento do banco via migrations
- **Bean Validation**
- **Maven**

## Funcionalidades

**Implementado:**
- Modelagem de domínio rica (Cliente, Conta Corrente e Conta Poupança) com regras de negócio encapsuladas
- Tratamento monetário com `BigDecimal` — precisão exata, sem erros de ponto flutuante
- Operações de depósito e saque com validações e cheque especial (conta corrente)
- Persistência desacoplada via Ports & Adapters, com Cliente salvo em PostgreSQL de ponta a ponta
- Mapeamento de herança das contas com Hibernate (estratégia *Single Table*)
- Migrations versionadas com Flyway

**Roadmap:**
- [ ] Casos de uso (incluindo transferência entre contas)
- [ ] API REST
- [ ] Documentação com Swagger / OpenAPI
- [ ] Autenticação e autorização com Spring Security + JWT
- [ ] Testes automatizados (JUnit 5, Mockito)
- [ ] Containerização com Docker
- [ ] Pipeline de CI/CD com GitHub Actions

## Como rodar localmente

### Pré-requisitos
- JDK 21
- PostgreSQL
- Maven (ou use o wrapper `./mvnw` incluído no projeto)

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/AlexsandroBaliero/bancodigital.git
   ```

2. Crie um banco de dados no PostgreSQL chamado `bancodigital`.

3. Defina a senha do banco na variável de ambiente `DB_PASSWORD`.

4. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

Na primeira execução, o Flyway cria automaticamente as tabelas a partir das migrations.

## Autor

**Alexsandro Baliero**
[linkedin.com/in/alexsandro-fernandes-99736568] · [https://github.com/AlexsandroBaliero]

---
*Projeto em desenvolvimento contínuo, parte da minha transição de carreira para o desenvolvimento backend.*
