CREATE TABLE inventory_item
(
    id IDENTITY PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    price    DOUBLE       NOT NULL,
    quantity INT          NOT NULL
);

ALTER TABLE inventory_item
    ADD UNIQUE (name, price);
