-- Tabela de Perfis genérica (relacionada com Cliente e Tecnico separadamente)

CREATE TABLE Cliente (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255),
                         cpf VARCHAR(14) UNIQUE NOT NULL,
                         telefone VARCHAR(15),
                         endereco VARCHAR(255),
                         data_nascimento DATE,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         data_criacao DATE DEFAULT CURRENT_DATE
);

CREATE TABLE Tecnico (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255),
                         cpf VARCHAR(14) UNIQUE NOT NULL,
                         telefone VARCHAR(15),
                         endereco VARCHAR(255),
                         data_nascimento DATE,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         data_criacao DATE DEFAULT CURRENT_DATE
);

-- Perfis de Cliente
CREATE TABLE perfis_cliente (
cliente_id INT NOT NULL,
perfil INT NOT NULL,
PRIMARY KEY (cliente_id, perfil),
FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

-- Perfis de Técnico
CREATE TABLE perfis_tecnico (
tecnico_id INT NOT NULL,
perfil INT NOT NULL,
PRIMARY KEY (tecnico_id, perfil),
FOREIGN KEY (tecnico_id) REFERENCES Tecnico(id)
);

-- Chamado
CREATE TABLE Chamado (
id INT AUTO_INCREMENT PRIMARY KEY,
data_abertura DATE DEFAULT CURRENT_DATE,
data_fechamento DATE,
prioridade INT NOT NULL,
status INT NOT NULL,
titulo VARCHAR(255) NOT NULL,
observacoes TEXT,
                         valor DECIMAL(10,2),
                         tecnico_id INT NOT NULL,
                         cliente_id INT NOT NULL,
                         FOREIGN KEY (tecnico_id) REFERENCES Tecnico(id),
                         FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

-- Despesa Fixa
CREATE TABLE despesa_fixa (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              descricao VARCHAR(255) NOT NULL,
                              valor DECIMAL(10,2),
                              data_vencimento DATE,
                              data_pagamento DATE,
                              status VARCHAR(20)
);
