CREATE TABLE IF NOT EXISTS pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_date DATE,
    customer_id INT,
    status VARCHAR(50),
    valor_total DECIMAL(10, 2)
    );

CREATE TABLE itens_pedidos (
    id BIGINT PRIMARY KEY,
    quantity INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    product_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL
);

INSERT INTO pedidos (order_date, customer_id, status, valor_total) VALUES (CURRENT_DATE, 1, 'NOVO', 459.5);

INSERT INTO itens_pedidos (id, quantity, total, product_id, order_id) VALUES (1, 2, 199.80, 1, 1);
INSERT INTO itens_pedidos (id, quantity, total, product_id, order_id) VALUES (2, 1, 59.90, 2, 1);