# ADR 0002 — Fluxo de Convite para Cadastro de Paciente

**Data:** 01/07/2026 
**Status:** Aceito

## Contexto
Pacientes precisam ter acesso ao app para registrar o próprio peso,
mas não podem se cadastrar livremente — dados de saúde exigem
controle de quem acessa o sistema.

## Decisão
A médica cadastra o paciente e o sistema gera um token único
com expiração de 48h. O paciente recebe um link e define
a própria senha ao primeiro acesso.

## Motivo
- Controle total da médica sobre quem entra no sistema
- Sem exposição de dados sensíveis a cadastros não autorizados
- Padrão adotado por sistemas profissionais (Slack, Trello, etc.)

## Consequências
- `status_conta` começa como `convidado` e vira `ativo` após ativação
- Convites expirados podem ser reenviados pela médica
- Histórico de tentativas de convite fica registrado na tabela CONVITE