# ADR-001: Escolha da stack tecnológica

- **Status:** Aceita
- **Data:** 2026-10-26

## Contexto
Precisamos hospedar uma aplicação SaaS em uma única VM Linux, com
infraestrutura simples de operar e conhecida pela equipe.

## Decisão
- Banco de dados: MySQL
- Servidor de aplicação: Apache Tomcat 10 (Jakarta EE)
- Linguagem: Java 11+ com Servlets e JDBC, build com Maven
- Código versionado no GitHub; instalação na VM via `git clone`

## Alternativas consideradas
- PostgreSQL: boa opção, mas a equipe já conhece MySQL.
- Spring Boot com Tomcat embutido: mais recursos, porém mais complexo
  para o escopo atual.

## Consequências
- Positivas: tecnologias maduras, serviços controlados via `systemctl`,
  implantação simples (um arquivo .war).
- Negativas: tudo em uma VM é ponto único de falha; escalar exigirá
  separar banco e aplicação no futuro.