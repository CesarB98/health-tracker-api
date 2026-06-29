# ADR 0001 — Escolha da Stack Técnica

**Data:** 2025-01  
**Status:** Aceito

---

## Contexto

Sistema de acompanhamento médico para emagrecimento com dois perfis
de usuário (médica e paciente) acessando o mesmo backend. Projeto
desenvolvido por estudante de Sistemas de Informação como portfólio
e ferramenta real para uso clínico.

## Decisão

| Camada | Escolha |
|--------|---------|
| Backend | Java 17 + Spring Boot 3 |
| Banco | PostgreSQL |
| Migrações | Flyway |
| Mobile | Flutter (dois apps separados, mesma API) |
| Autenticação | JWT com roles MEDICO e PACIENTE |
| Arquitetura backend | Controller → Service → Repository |

## Motivo

**Flutter** está sendo cursado na disciplina de desenvolvimento
cross-platform na faculdade, permitindo aplicar o aprendizado
acadêmico em caso de uso real. A sintaxe de Dart é próxima de Java,
reduzindo a curva de aprendizado.

**Java + Spring Boot** é robusto para sistemas de saúde, amplamente
adotado no mercado e já familiar pelo histórico acadêmico do
desenvolvedor.

**PostgreSQL** foi escolhido por ser relacional (dados médicos têm
relacionamentos claros entre si), gratuito e com excelente suporte
no ecossistema Spring via JPA/Hibernate.

**Flyway** para versionar as migrações do banco — cada mudança no
schema vira um arquivo SQL numerado, com histórico rastreável no Git.

## Opções descartadas

- **React Native** — descartado em favor do Flutter pela matéria
  em curso na faculdade e pela similaridade de Dart com Java.
- **MySQL** — descartado em favor do PostgreSQL por robustez e
  suporte a tipos de dados mais ricos.
- **Firebase** — descartado por não oferecer controle total sobre
  os dados, o que é crítico em sistemas de saúde (LGPD).

## Consequências

- O mesmo backend serve os dois apps Flutter.
- Regras de acesso controladas por role no token JWT.
- Nenhuma duplicação de lógica de negócio entre as interfaces.
- Para expandir para múltiplos médicos no futuro, apenas novas
  regras de negócio serão necessárias — o banco já está preparado
  via tabela de junção MEDICO_PACIENTE.
