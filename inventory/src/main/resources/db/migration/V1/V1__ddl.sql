CREATE TABLE inventory.product_category
(
    id   bigserial PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE inventory.product
(
    id          bigserial PRIMARY KEY,
    name        varchar(100) NOT NULL,
    price       real         NOT NULL,
    description varchar(2000),
    category_id integer      NOT NULL,
    UNIQUE (name, price),
    CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES inventory.product_category (id)
);

CREATE TABLE inventory.product_property
(
    id          bigserial PRIMARY KEY,
    name        varchar(100) NOT NULL,
    description varchar(1000),
    product_id  integer      NOT NULL,
    UNIQUE (name, product_id),
    CONSTRAINT fk_product_property_product_id FOREIGN KEY (product_id) REFERENCES inventory.product (id)
);

CREATE TABLE inventory.inventory_item
(
    id         bigserial PRIMARY KEY,
    quantity   integer NOT NULL,
    product_id integer NOT NULL UNIQUE,
    CONSTRAINT fk_inventory_item_product_id FOREIGN KEY (product_id) REFERENCES inventory.product (id)
);