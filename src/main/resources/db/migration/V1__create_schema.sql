CREATE TABLE clientes (
    id   BIGSERIAL    PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf  VARCHAR(11)  NOT NULL UNIQUE
);

CREATE TABLE contas (
    id         BIGSERIAL     PRIMARY KEY,
    numero     VARCHAR(20)   NOT NULL UNIQUE,
    saldo      NUMERIC(19,2) NOT NULL DEFAULT 0,
    tipo       VARCHAR(31)   NOT NULL,
    limite     NUMERIC(19,2),
    cliente_id BIGINT        NOT NULL REFERENCES clientes(id)
);