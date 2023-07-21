-- Inserir dados na tabela grupos
INSERT INTO grupos (id, nome, descricao) VALUES (1, 'ADMINISTRADORES', 'Grupo de administradores do sistema');
INSERT INTO grupos (id, nome, descricao) VALUES (2, 'VENDEDORES', 'Grupo de vendedores');
INSERT INTO grupos (id, nome, descricao) VALUES (3, 'AUXILIARES', 'Grupo de auxiliares');

-- Inserir dados na tabela usuarios
INSERT INTO usuarios (id, nome, email, senha) VALUES (1, 'João Silva', 'joao@example.com', '$2a$12$uzRtoqk3ODmeOL2r9CnBEuvgOV1AZ2.h5iNMssr4r5UfrniRs4tYa');
INSERT INTO usuarios (id, nome, email, senha) VALUES (2, 'Maria Santos', 'maria@example.com', '$2a$12$Pm3OkPH3hVV3kqt96L/U6uSl4XvonUE0OphGPRhsvIUdOWYr6.P92');
INSERT INTO usuarios (id, nome, email, senha) VALUES (3, 'Ana Nascimento', 'ana@example.com', '$2a$12$NPyuA3zMGqLFs5pMeRI5meqbsnOitOTisWzAWslhPzOAanoN.JV7y');

-- Inserir dados na tabela usuario_grupo
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (1, 1);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (2, 2);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (3, 3);

-- Inserir dados na tabela categorias
INSERT INTO categorias (id, descricao, categoria_pai_id) VALUES (1, 'Eletrônicos', NULL);
INSERT INTO categorias (id, descricao, categoria_pai_id) VALUES (2, 'Roupas', NULL);
INSERT INTO categorias (id, descricao, categoria_pai_id) VALUES (3, 'Livros', NULL);
INSERT INTO categorias (id, descricao, categoria_pai_id) VALUES (4, 'Video Games', 1);
INSERT INTO categorias (id, descricao, categoria_pai_id) VALUES (5, 'Smartphones', 1);
INSERT INTO categorias (id, descricao, categoria_pai_id) VALUES (6, 'Camisetas', 2);
INSERT INTO categorias (id, descricao, categoria_pai_id) VALUES (7, 'Fantasia', 3);

-- Inserir dados na tabela produtos
INSERT INTO produtos (id, nome, sku, valor_unitario, quantidade_estoque, categoria_id) VALUES (1, 'Galaxy S21 FE 5G 128GB', 'CE0001', 1500.00, 10, 5);
INSERT INTO produtos (id, nome, sku, valor_unitario, quantidade_estoque, categoria_id) VALUES (2, 'Camiseta Polo', 'CA0001', 50.00, 20, 6);
INSERT INTO produtos (id, nome, sku, valor_unitario, quantidade_estoque, categoria_id) VALUES (3, 'O Senhor dos Anéis', 'LI0001', 30.00, 15, 7);

-- Inserir dados na tabela clientes
INSERT INTO clientes (id, nome, email, doc_receita_federal, tipo) VALUES (1, 'Fulano de Tal', 'fulano@example.com', '123.456.789-00', 'FISICA');
INSERT INTO clientes (id, nome, email, doc_receita_federal, tipo) VALUES (2, 'Empresa ABC', 'empresa@example.com', '12.345.678/0001-00', 'JURIDICA');

-- Inserir dados na tabela enderecos
INSERT INTO enderecos (id, logradouro, numero, complemento, cidade, uf, cep, cliente_id) VALUES (1, 'Rua A', '123', 'Apto 101', 'São Paulo', 'SP', '01234-567', 1);
INSERT INTO enderecos (id, logradouro, numero, complemento, cidade, uf, cep, cliente_id) VALUES (2, 'Avenida B', '456', 'Loja 02', 'Rio de Janeiro', 'RJ', '12345-678', 2);

-- Inserir dados na tabela pedidos
INSERT INTO pedidos (id, data_criacao, observacao, data_entrega, valor_frete, valor_desconto, valor_total, status, forma_pagamento, vendedor_id, cliente_id, entrega_logradouro, entrega_numero, entrega_complemento, entrega_cidade, entrega_uf, entrega_cep) VALUES (1, CURRENT_TIMESTAMP, 'Pedido de teste 1', '2023-07-14', 20.00, 10.00, 1500.00, 'EMITIDO', 'CARTAO_CREDITO', 1, 1, 'Rua A', '123', 'Apto 101', 'São Paulo', 'SP', '01234-567');
INSERT INTO pedidos (id, data_criacao, observacao, data_entrega, valor_frete, valor_desconto, valor_total, status, forma_pagamento, vendedor_id, cliente_id, entrega_logradouro, entrega_numero, entrega_complemento, entrega_cidade, entrega_uf, entrega_cep) VALUES (2, CURRENT_TIMESTAMP, 'Pedido de teste 2', '2023-07-15', 10.00, 5.00, 100.00, 'EMITIDO', 'BOLETO_BANCARIO', 2, 2, 'Avenida B', '456', 'Loja 02', 'Rio de Janeiro', 'RJ', '12345-678');

-- Inserir dados na tabela itens_pedido
INSERT INTO itens_pedido (id, quantidade, valor_unitario, produto_id, pedido_id) VALUES (1, 2, 1500.00, 1, 1);
INSERT INTO itens_pedido (id, quantidade, valor_unitario, produto_id, pedido_id) VALUES (2, 3, 50.00, 2, 1);
INSERT INTO itens_pedido (id, quantidade, valor_unitario, produto_id, pedido_id) VALUES (3, 1, 30.00, 3, 2);
