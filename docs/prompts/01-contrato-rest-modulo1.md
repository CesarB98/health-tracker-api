# Prompt 01 — Contrato REST Módulo 1

**Etapa:** Definição do contrato REST do Módulo 1 (Cadastro e Autenticação)  
**Data:** 2025-01  
**Resultado:** docs/contrato-api.md

---

## Prompt utilizado

```
Aja como um engenheiro de software backend especialista em Java/Spring Boot
e design de APIs REST para sistemas de saúde.

CONTEXTO DO PROJETO:
- Sistema de acompanhamento médico para emagrecimento, com duas interfaces:
  app Flutter da médica e app Flutter do paciente, consumindo a mesma API.
- Stack: Java + Spring Boot + Spring Security (JWT) + PostgreSQL + Flyway.
- Arquitetura em camadas: Controller → Service → Repository.
- Modelo de dados já fechado para o MVP: MEDICO, PACIENTE, MEDICO_PACIENTE,
  CONVITE, REGISTRO_PESO, CONTATO_EMERGENCIA.
- Dois papéis de usuário (roles): MEDICO e PACIENTE, autenticados via JWT,
  com regras de acesso diferentes nos mesmos endpoints.

TAREFA:
Defina o contrato REST completo do Módulo 1 (cadastro de pacientes e
autenticação), cobrindo o fluxo: médica cria paciente → sistema gera
convite → paciente ativa conta → login → registro de peso por ambos os
papéis.

Para cada endpoint, especifique:
1. Método HTTP e rota (seguindo convenção REST, recursos no plural)
2. Quem pode chamar (role MEDICO, PACIENTE, ou público/sem autenticação)
3. Request body (campos e tipos)
4. Response body em caso de sucesso (código HTTP + estrutura JSON)
5. Possíveis erros relevantes (código HTTP + quando ocorrem)
6. Regra de autorização específica

RESTRIÇÕES:
- Sem endpoints de exclusão física (DELETE) de paciente — usar inativação.
- Senhas nunca aparecem em nenhum response.
- Pensar em paginação para listagem de pacientes.
- Indicar quais campos são obrigatórios vs opcionais em cada request.

FORMATO DE SAÍDA:
Lista organizada por recurso (Autenticação, Convite, Paciente,
Registro de Peso), pronta para usar como referência ao implementar
os Controllers no Spring Boot.
```
