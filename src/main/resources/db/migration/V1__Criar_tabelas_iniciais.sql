-- Criação da tabela base para a hierarquia de Pessoa
CREATE TABLE pessoa (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        cpf VARCHAR(14) NOT NULL UNIQUE,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        senha VARCHAR(255) NOT NULL,
                        data_criacao DATE DEFAULT CURRENT_DATE
);

-- Tabela para armazenar os perfis associados a cada Pessoa
CREATE TABLE perfil (
                        pessoa_id INT NOT NULL,
                        perfis INT NOT NULL,
                        PRIMARY KEY (pessoa_id, perfis),
                        FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);

-- Tabela Cliente (herda de Pessoa – estratégia JOINED)
CREATE TABLE cliente (
                         id INT PRIMARY KEY,
                         FOREIGN KEY (id) REFERENCES pessoa(id)
);

-- Tabela Técnico (herda de Pessoa – estratégia JOINED)
CREATE TABLE tecnico (
                         id INT PRIMARY KEY,
                         FOREIGN KEY (id) REFERENCES pessoa(id)
);

-- Tabela Chamado
CREATE TABLE chamado (
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
                         FOREIGN KEY (tecnico_id) REFERENCES tecnico(id),
                         FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabela Despesa_Fixa
CREATE TABLE despesa_fixa (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              descricao VARCHAR(255) NOT NULL,
                              valor DECIMAL(10,2),
                              data_vencimento DATE,
                              data_pagamento DATE,
                              status VARCHAR(20)
);