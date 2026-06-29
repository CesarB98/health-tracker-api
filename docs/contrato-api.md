# Contrato REST — Módulo 1: Cadastro e Autenticação

---

## Autenticação

### POST /auth/login
**Acesso:** Público

**Request body:**
```json
{
  "email": "string (obrigatório)",
  "senha": "string (obrigatório)"
}
```

**Response 200:**
```json
{
  "token": "jwt...",
  "role": "MEDICO | PACIENTE",
  "id": "uuid",
  "nome": "string"
}
```

**Erros:**
- `401` — Credenciais inválidas
- `403` — Conta com status_conta = 'convidado' (ainda não ativou)

---

### POST /auth/ativar-conta
**Acesso:** Público (requer token de convite válido)

**Request body:**
```json
{
  "token": "string (obrigatório)",
  "senha": "string (obrigatório, mín. 8 caracteres)"
}
```

**Response 200:**
```json
{
  "token": "jwt...",
  "role": "PACIENTE",
  "id": "uuid"
}
```

**Erros:**
- `404` — Token não encontrado
- `410` — Token expirado
- `409` — Conta já ativada

---

### POST /auth/refresh
**Acesso:** Token válido (header Authorization)

**Request body:** vazio

**Response 200:**
```json
{
  "token": "novo_jwt..."
}
```

**Erros:**
- `401` — Token inválido ou expirado

---

## Convite

### POST /pacientes/{pacienteId}/convites
**Acesso:** Role MEDICO  
**Regra:** Apenas o médico vinculado ao paciente pode gerar convite.

**Request body:**
```json
{
  "canal_envio": "email | sms (obrigatório)"
}
```

**Response 201:**
```json
{
  "id": "uuid",
  "expira_em": "timestamp",
  "status": "pendente"
}
```

**Erros:**
- `404` — Paciente não encontrado ou não vinculado
- `409` — Paciente já ativo
- `422` — Canal de envio inválido

---

### POST /convites/{conviteId}/reenviar
**Acesso:** Role MEDICO  
**Regra:** Apenas o médico vinculado ao paciente pode reenviar.

**Request body:** vazio

**Response 201:**
```json
{
  "id": "uuid",
  "expira_em": "timestamp"
}
```

**Erros:**
- `404` — Convite não encontrado
- `409` — Convite já aceito

---

### GET /convites/{token}/validar
**Acesso:** Público

**Response 200:**
```json
{
  "valido": true,
  "nome_paciente": "string",
  "expira_em": "timestamp"
}
```

**Erros:**
- `404` — Token inválido
- `410` — Token expirado

---

## Paciente

### POST /pacientes
**Acesso:** Role MEDICO

**Request body:**
```json
{
  "nome": "string (obrigatório)",
  "cpf": "string (obrigatório)",
  "data_nascimento": "date (obrigatório)",
  "sexo": "string (opcional)",
  "telefone": "string (obrigatório)",
  "email": "string (obrigatório)",
  "altura_cm": "float (opcional)",
  "peso_meta_kg": "float (opcional)"
}
```

**Response 201:**
```json
{
  "id": "uuid",
  "nome": "string",
  "status_conta": "convidado",
  "criado_em": "timestamp"
}
```

**Erros:**
- `409` — CPF ou e-mail já cadastrado
- `422` — Campos obrigatórios ausentes ou inválidos

> Cria automaticamente vínculo em MEDICO_PACIENTE com `principal = true`.

---

### GET /pacientes
**Acesso:** Role MEDICO  
**Regra:** Retorna apenas pacientes vinculados ao médico autenticado.

**Query params:**
- `page` (int, default 0)
- `size` (int, default 20)
- `ativo` (boolean, opcional, default true)
- `busca` (string, opcional — filtra por nome)

**Response 200:**
```json
{
  "content": [
    {
      "id": "uuid",
      "nome": "string",
      "status_conta": "string",
      "ultimo_peso_kg": "float | null",
      "ativo": true
    }
  ],
  "totalElements": 0,
  "totalPages": 0,
  "page": 0
}
```

---

### GET /pacientes/{id}
**Acesso:** Role MEDICO ou PACIENTE  
**Regra:** Médica vê pacientes vinculados. Paciente vê apenas o próprio id.

**Response 200:**
```json
{
  "id": "uuid",
  "nome": "string",
  "cpf": "string",
  "data_nascimento": "date",
  "sexo": "string",
  "telefone": "string",
  "email": "string",
  "altura_cm": "float",
  "peso_meta_kg": "float",
  "status_conta": "string",
  "ativo": true,
  "criado_em": "timestamp"
}
```

**Erros:**
- `403` — Sem permissão
- `404` — Não encontrado

---

### PUT /pacientes/{id}
**Acesso:** Role MEDICO ou PACIENTE  
**Regra:** Médica edita todos os campos. Paciente edita apenas `telefone`, `email`, `peso_meta_kg`.

**Response 200:** Objeto paciente atualizado (mesma estrutura do GET)

**Erros:**
- `403` — Campo não permitido para o role
- `404` — Não encontrado
- `422` — CPF ou e-mail duplicado

---

### PATCH /pacientes/{id}/inativar
**Acesso:** Role MEDICO

**Request body:** vazio

**Response 200:**
```json
{ "id": "uuid", "ativo": false }
```

**Erros:**
- `403` — Sem permissão
- `404` — Não encontrado
- `409` — Já inativo

---

### PATCH /pacientes/{id}/reativar
**Acesso:** Role MEDICO

**Response 200:**
```json
{ "id": "uuid", "ativo": true }
```

**Erros:**
- `403` — Sem permissão
- `404` — Não encontrado
- `409` — Já ativo

---

## Registro de Peso

### POST /pacientes/{id}/registros-peso
**Acesso:** Role MEDICO ou PACIENTE  
**Regra:** Médica registra para qualquer paciente vinculado. Paciente registra apenas para o próprio id.

**Request body:**
```json
{
  "peso_kg": "float (obrigatório)",
  "observacao": "string (opcional)"
}
```

> `origem` e `registrado_por` são preenchidos automaticamente pelo backend via JWT.

**Response 201:**
```json
{
  "id": "uuid",
  "peso_kg": 78.5,
  "imc_calculado": 24.2,
  "origem": "consulta | auto_registro",
  "registrado_em": "timestamp"
}
```

**Erros:**
- `403` — Sem permissão
- `404` — Paciente não encontrado
- `422` — Peso inválido (≤ 0 ou > 500)

---

### GET /pacientes/{id}/registros-peso
**Acesso:** Role MEDICO ou PACIENTE  
**Regra:** Mesma do GET de paciente.

**Query params:**
- `page` (int, default 0)
- `size` (int, default 50)
- `data_inicio` (date, opcional)
- `data_fim` (date, opcional)

**Response 200:**
```json
{
  "content": [
    {
      "id": "uuid",
      "peso_kg": 78.5,
      "imc_calculado": 24.2,
      "origem": "auto_registro",
      "registrado_em": "timestamp"
    }
  ],
  "totalElements": 0,
  "totalPages": 0
}
```

> Ordenado por `registrado_em` decrescente.

---

### DELETE /registros-peso/{registroId}
**Acesso:** Role MEDICO ou PACIENTE  
**Regra:** Apenas o autor do registro pode excluir, dentro de uma janela de 1 hora após o registro.

**Response 204:** Sem body

**Erros:**
- `403` — Não é o autor ou janela de exclusão expirada
- `404` — Registro não encontrado
