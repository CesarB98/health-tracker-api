# Health Tracker — Sistema de Acompanhamento Médico

Sistema para médicas acompanharem pacientes em programas de emagrecimento,
com app da médica e app do paciente conectados à mesma API REST.

## Stack

| Camada | Tecnologia |
|--------|-----------|
| Backend | Java 17 + Spring Boot 3 |
| Banco de dados | PostgreSQL + Flyway |
| Mobile | Flutter (app médica + app paciente) |
| Autenticação | JWT com roles (MEDICO / PACIENTE) |
| Arquitetura | Controller → Service → Repository |

## Estrutura do repositório

```
health-tracker-api/
├── docs/
│   ├── modelo-dados.md        # Diagrama ER e decisões do banco
│   ├── contrato-api.md        # Endpoints REST por módulo
│   ├── decisoes/              # ADRs — decisões de arquitetura
│   │   └── 0001-stack-tecnica.md
│   └── prompts/               # Prompts de engenheiro por etapa
│       └── 01-contrato-rest-modulo1.md
├── src/                       # Código fonte Spring Boot (em breve)
└── README.md
```

## Módulos do MVP

- [x] Planejamento e modelagem
- [ ] Módulo 1 — Cadastro de pacientes + autenticação
- [ ] Módulo 2 — Prontuário eletrônico
- [ ] Módulo 3 — Receitas médicas
- [ ] Módulo 4 — Calendário de consultas

## Status atual

🟡 Em desenvolvimento — Módulo 1 em andamento

## Contexto do projeto

Desenvolvido como projeto de portfólio e ferramenta real para uso clínico.
A médica cadastra pacientes, envia convite de acesso e acompanha a evolução
de peso ao longo do tratamento. O paciente registra o próprio peso pelo app
e visualiza suas metas e receitas.