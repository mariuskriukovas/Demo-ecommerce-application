CREATE TABLE shop_user
(
    id IDENTITY PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL
);

ALTER TABLE shop_user
    ADD UNIQUE (username);

ALTER TABLE shop_user
    ADD UNIQUE (email);