CREATE TABLE product_category
(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE product
(
    id IDENTITY PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    price       DOUBLE        NOT NULL,
    description VARCHAR(2000) NOT NULL,
    category_id INT
);

CREATE TABLE product_property
(
    id IDENTITY PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    description VARCHAR(1000) NOT NULL,
    product_id  INT
);

CREATE TABLE inventory_item
(
    id IDENTITY PRIMARY KEY,
    quantity   INT NOT NULL,
    product_id INT
);


ALTER TABLE product_category
    ADD UNIQUE (name);

ALTER TABLE product
    ADD UNIQUE (name, price);

ALTER TABLE product
    ADD FOREIGN KEY (category_id) REFERENCES product_category (id);

ALTER TABLE product_property
    ADD UNIQUE (name);

ALTER TABLE product_property
    ADD FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE inventory_item
    ADD UNIQUE (product_id);

ALTER TABLE inventory_item
    ADD FOREIGN KEY (product_id) REFERENCES product (id);