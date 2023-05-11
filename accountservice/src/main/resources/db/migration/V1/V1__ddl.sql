CREATE TABLE account.shop_user
(
    id       bigserial PRIMARY KEY,
    username varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    email    varchar(100) NOT NULL UNIQUE
);