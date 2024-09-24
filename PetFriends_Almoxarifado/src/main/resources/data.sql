CREATE TABLE estoques (
    id INT PRIMARY KEY,
    product_Id INT NOT NULL,
    quantity INT NOT NULL,
    prateleira VARCHAR(255),
    corredor VARCHAR(255)
);

INSERT INTO estoques (id, product_Id, quantity, prateleira, corredor) VALUES
                                                                          (1,1, 50, 'A1', '1'),
                                                                          (2,2, 30, 'B2', '2'),
                                                                          (3,3, 20, 'C3', '1'),
                                                                          (4,4, 10, 'D1', '3'),
                                                                          (5,5, 100, 'E4', '2');