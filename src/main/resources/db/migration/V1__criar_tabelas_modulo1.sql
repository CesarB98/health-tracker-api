-- ============================================================
-- V1 — Criação das tabelas do Módulo 1 (Cadastro e Autenticação)
-- Health Tracker API
-- ============================================================

-- Extensão para geração de UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ------------------------------------------------------------
-- MEDICO
-- ------------------------------------------------------------
CREATE TABLE medico (
    id               UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    nome             VARCHAR(150) NOT NULL,
    crm              VARCHAR(20)  NOT NULL UNIQUE,
    especialidade    VARCHAR(100),
    email            VARCHAR(150) NOT NULL UNIQUE,
    senha_hash       VARCHAR(255) NOT NULL,
    criado_em        TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------
-- PACIENTE
-- ------------------------------------------------------------
CREATE TABLE paciente (
    id               UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    nome             VARCHAR(150) NOT NULL,
    cpf              VARCHAR(14)  NOT NULL UNIQUE,
    data_nascimento  DATE         NOT NULL,
    sexo             VARCHAR(20),
    telefone         VARCHAR(20)  NOT NULL,
    email            VARCHAR(150) NOT NULL UNIQUE,
    senha_hash       VARCHAR(255),
    status_conta     VARCHAR(20)  NOT NULL DEFAULT 'convidado'
                         CHECK (status_conta IN ('convidado', 'ativo')),
    altura_cm        NUMERIC(5,2),
    peso_meta_kg     NUMERIC(5,2),
    ativo            BOOLEAN      NOT NULL DEFAULT TRUE,
    criado_em        TIMESTAMP    NOT NULL DEFAULT NOW(),
    atualizado_em    TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------
-- MEDICO_PACIENTE (tabela de junção N:N)
-- ------------------------------------------------------------
CREATE TABLE medico_paciente (
    id               UUID      PRIMARY KEY DEFAULT gen_random_uuid(),
    medico_id        UUID      NOT NULL REFERENCES medico(id),
    paciente_id      UUID      NOT NULL REFERENCES paciente(id),
    data_inicio      DATE      NOT NULL DEFAULT CURRENT_DATE,
    principal        BOOLEAN   NOT NULL DEFAULT TRUE,
    UNIQUE (medico_id, paciente_id)
);

-- ------------------------------------------------------------
-- CONVITE
-- ------------------------------------------------------------
CREATE TABLE convite (
    id                    UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id           UUID         NOT NULL REFERENCES paciente(id),
    criado_por_medico_id  UUID         NOT NULL REFERENCES medico(id),
    token                 VARCHAR(255) NOT NULL UNIQUE,
    canal_envio           VARCHAR(20)  NOT NULL
                              CHECK (canal_envio IN ('email', 'sms')),
    status                VARCHAR(20)  NOT NULL DEFAULT 'pendente'
                              CHECK (status IN ('pendente', 'aceito', 'expirado')),
    expira_em             TIMESTAMP    NOT NULL,
    aceito_em             TIMESTAMP,
    criado_em             TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------
-- REGISTRO_PESO
-- ------------------------------------------------------------
CREATE TABLE registro_peso (
    id               UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id      UUID         NOT NULL REFERENCES paciente(id),
    registrado_por   UUID         NOT NULL,
    origem           VARCHAR(20)  NOT NULL
                         CHECK (origem IN ('consulta', 'auto_registro')),
    peso_kg          NUMERIC(5,2) NOT NULL CHECK (peso_kg > 0 AND peso_kg <= 500),
    imc_calculado    NUMERIC(5,2),
    observacao       TEXT,
    registrado_em    TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------
-- CONTATO_EMERGENCIA
-- ------------------------------------------------------------
CREATE TABLE contato_emergencia (
    id               UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id      UUID         NOT NULL REFERENCES paciente(id),
    nome             VARCHAR(150) NOT NULL,
    parentesco       VARCHAR(50),
    telefone         VARCHAR(20)  NOT NULL
);

-- ------------------------------------------------------------
-- ÍNDICES para performance nas queries mais comuns
-- ------------------------------------------------------------
CREATE INDEX idx_paciente_ativo         ON paciente(ativo);
CREATE INDEX idx_paciente_status_conta  ON paciente(status_conta);
CREATE INDEX idx_medico_paciente_medico ON medico_paciente(medico_id);
CREATE INDEX idx_convite_token          ON convite(token);
CREATE INDEX idx_convite_paciente       ON convite(paciente_id);
CREATE INDEX idx_registro_peso_paciente ON registro_peso(paciente_id);
CREATE INDEX idx_registro_peso_data     ON registro_peso(registrado_em DESC);
