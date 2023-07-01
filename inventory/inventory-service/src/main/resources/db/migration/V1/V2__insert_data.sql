INSERT INTO inventory.product_category (uid, name)
VALUES ('fdf2545a-f869-11ed-b67e-0242ac120002', 'Laptop'),
       ('fdf25b08-f869-11ed-b67e-0242ac120002', 'Phone'),
       ('fdf25c7a-f869-11ed-b67e-0242ac120002', 'Tablet');

INSERT INTO inventory.product (uid, name, price, description, category_id)
VALUES ('2e6e9b48-f86a-11ed-b67e-0242ac120002', 'Apple MacBook Air (M2)', 1100.99,
        'The latest MacBook Air, powered by Apple’s M2 processor, is the best laptop for most people.',
        (select id from inventory.product_category where name = 'Laptop')),
       ('2e6e9f30-f86a-11ed-b67e-0242ac120002', 'MacBook Pro (14-inch and 16-inch)', 1600.99,
        'The best Macbook for creators',
        (select id from inventory.product_category where name = 'Laptop')),
       ('2e6ea07a-f86a-11ed-b67e-0242ac120002', 'HP Spectre x360 14', 1500.99,
        'The best Windows laptop',
        (select id from inventory.product_category where name = 'Laptop')),
       ('2e6ea502-f86a-11ed-b67e-0242ac120002', 'Samsung Galaxy S23 Ultra', 1100.99,
        'The best Android phone',
        (select id from inventory.product_category where name = 'Phone')),
       ('2e6ea6ec-f86a-11ed-b67e-0242ac120002', 'iPhone 14 Pro Max', 1200.99,
        'The best phone overall',
        (select id from inventory.product_category where name = 'Phone')),
       ('2e6ea8ae-f86a-11ed-b67e-0242ac120002', 'iPhone 14 Pro', 999.99,
        'The best smaller phone ever',
        (select id from inventory.product_category where name = 'Phone')),
       ('2e6eaa98-f86a-11ed-b67e-0242ac120002', 'Apple iPad Air (2022)', 1050.99,
        'The best tablet for everyone',
        (select id from inventory.product_category where name = 'Tablet')),
       ('', 'iPad 2022 (10th gen)', 700.99,
        'The best regular iPad in years',
        (select id from inventory.product_category where name = 'Tablet')),
       ('2e6eac32-f86a-11ed-b67e-0242ac120002', 'Samsung Galaxy Tab S8', 800.99,
        'The best premium Android tablet',
        (select id from inventory.product_category where name = 'Tablet'));

INSERT INTO inventory.product_property (uid, name,
                                        description,
                                        product_id)
VALUES ('ce55d298-f86a-11ed-b67e-0242ac120002', 'Color',
        'With an all-new design, the M2 MacBook Air comes in four color options: Midnight, Space Gray, Silver, and Starlight.',
        (select id from inventory.product where name = 'Apple MacBook Air (M2)')),
       ('ce55d5b8-f86a-11ed-b67e-0242ac120002', 'Camera',
        'Fortunately, the camera inside that notch is the same 1080p unit that''s in the larger Pro models, and it''s much improved over the crappy 720p camera that was in the older Air and the new 13-inch MacBook Pro',
        (select id from inventory.product where name = 'Apple MacBook Air (M2)')),
       ('ce55d6bc-f86a-11ed-b67e-0242ac120002', 'Screen',
        'The MacBook Air M2 has caught up to the MacBook Pro M2 in terms of display quality, and that''s a big deal. You get a bright and colorful 13.6-inch panel (2560 x 1664 pixels) for watching videos, editing photos and everything else you want to do with no trade-offs.',
        (select id from inventory.product where name = 'Apple MacBook Air (M2)'));

INSERT INTO inventory.inventory_item (uid, quantity,
                                      product_id)
VALUES ('f2c579b2-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'Apple MacBook Air (M2)')),
       ('f2c57c3c-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'MacBook Pro (14-inch and 16-inch)')),
       ('f2c57d68-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'HP Spectre x360 14')),
       ('f2c58182-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'Samsung Galaxy S23 Ultra')),
       ('f2c582b8-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'iPhone 14 Pro Max')),
       ('f2c583e4-f86a-11ed-b67e-0242ac120002', 100, (select id from inventory.product where name = 'iPhone 14 Pro')),
       ('f2c584f2-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'Apple iPad Air (2022)')),
       ('f2c58600-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'iPad 2022 (10th gen)')),
       ('f2c58722-f86a-11ed-b67e-0242ac120002', 100,
        (select id from inventory.product where name = 'Samsung Galaxy Tab S8'));