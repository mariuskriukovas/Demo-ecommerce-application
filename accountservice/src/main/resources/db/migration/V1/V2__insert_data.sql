INSERT INTO account.shop_user (uid,
                               username,
                               password,
                               email)
VALUES ('81de5e6e-f0c0-11ed-a05b-0242ac120003', 'userA', '$2a$10$XCBUcZPNaKakw5HRAjKk9OKo0iZkq0doEJUxRkXeyl/PqofG6z2LK',
        'userA@gmail.com'),
       ('81de61c0-f0c0-11ed-a05b-0242ac120003', 'userB', '$2a$12$zc43GqFHIpHK.eUQN.14UuRoPmfpu6IQlcqzg4d5b9UrdYf0N4xz6',
        'userB@gmail.com'),
       ('81de6454-f0c0-11ed-a05b-0242ac120003', 'userC', '$2a$12$O8B2LDq7egMeD0usZvmHTeIpE4auaAyQbC2GKvn3jWJFBxDdr3N9C',
        'userC@gmail.com');

INSERT INTO account.role (user_id,
                          role)
VALUES ((select id from account.shop_user where username = 'userA'), 'USER'),
       ((select id from account.shop_user where username = 'userB'), 'USER'),
       ((select id from account.shop_user where username = 'userC'), 'ADMIN');