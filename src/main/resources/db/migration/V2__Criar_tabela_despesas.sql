CREATE TABLE despesas (
id               SERIAL PRIMARY KEY,
descricao        VARCHAR(255) NOT NULL,
valor            DECIMAL(10, 2),
data_vencimento  DATE,
status           VARCHAR(50)
);