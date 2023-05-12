CREATE TABLE account.shop_user
(
    id       bigserial PRIMARY KEY,
    username varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    email    varchar(100) NOT NULL UNIQUE
);

CREATE TABLE account.role
(
    id      bigserial PRIMARY KEY,
    user_id integer      NOT NULL,
    role    varchar(100) NOT NULL,
    CONSTRAINT fk_role_user_id FOREIGN KEY (user_id) REFERENCES account.shop_user (id),
    UNIQUE (user_id, role)
);