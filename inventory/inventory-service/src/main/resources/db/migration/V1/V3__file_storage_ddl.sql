CREATE TABLE inventory.file_metadata
(
    id             bigserial PRIMARY KEY,
    uid            varchar(40)  NOT NULL UNIQUE,
    file_name      varchar(500) NOT NULL,
    file_extension varchar(10)  NOT NULL,
    file_key       varchar(100) NOT NULL UNIQUE,
    s3_url         varchar(500) NOT NULL UNIQUE
);

CREATE TABLE inventory.product_file
(
    id         bigserial PRIMARY KEY,
    uid        varchar(40) NOT NULL UNIQUE,
    product_id integer     NOT NULL,
    file_id    integer     NOT NULL,
    UNIQUE (product_id, file_id),
    CONSTRAINT fk_product_file_product_id FOREIGN KEY (product_id) REFERENCES inventory.product (id),
    CONSTRAINT fk_product_file_file_metadata_id FOREIGN KEY (file_id) REFERENCES inventory.file_metadata (id)
);