-- 1) Tabela de Empresas
CREATE TABLE empresa (
id INT AUTO_INCREMENT PRIMARY KEY,
razao_social    VARCHAR(100) NOT NULL,
nome_fantasia   VARCHAR(100) NOT NULL,
cnpj            VARCHAR(14)  NOT NULL,
email           VARCHAR(100) NOT NULL,
telefone        VARCHAR(20),
data_criacao    DATE         NOT NULL,
CONSTRAINT uk_empresa_cnpj   UNIQUE (cnpj),
CONSTRAINT uk_empresa_email  UNIQUE (email)
);

-- 2) Tabela de Chamados de Empresa
CREATE TABLE chamado_empresa (
id              INT             AUTO_INCREMENT PRIMARY KEY,
data_abertura   DATE            NOT NULL,
data_fechamento DATE,
data_visita     DATE,
prioridade      VARCHAR(20)     NOT NULL,
status          VARCHAR(20)     NOT NULL,
titulo          VARCHAR(255)    NOT NULL,
observacoes     TEXT,
tecnico_id      INT             NOT NULL,
empresa_id      INT             NOT NULL,
valor           DECIMAL(10,2)   NOT NULL,
CONSTRAINT      fk_chamadoempresa_tecnico
FOREIGN KEY     (tecnico_id)
REFERENCES      tecnico(id),
CONSTRAINT      fk_chamadoempresa_empresa
FOREIGN KEY     (empresa_id)
REFERENCES      empresa(id)
);