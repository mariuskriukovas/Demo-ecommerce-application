INSERT INTO PUBLIC.product_category (name)
VALUES ('Laptop'),
       ('Phone'),
       ('Tablet');

INSERT INTO PUBLIC.product (name, price, description, category_id)
VALUES ('Apple MacBook Air (M2)', 1100.99,
        'The latest MacBook Air, powered by Apple’s M2 processor, is the best laptop for most people.',
        (select id from product_category where name = 'Laptop')),
       ('MacBook Pro (14-inch and 16-inch)', 1600.99,
        'The best Macbook for creators',
        (select id from product_category where name = 'Laptop')),
       ('HP Spectre x360 14', 1500.99,
        'The best Windows laptop',
        (select id from product_category where name = 'Laptop')),
       ('Samsung Galaxy S23 Ultra', 1100.99,
        'The best Android phone',
        (select id from product_category where name = 'Phone')),
       ('iPhone 14 Pro Max', 1200.99,
        'The best phone overall',
        (select id from product_category where name = 'Phone')),
       ('iPhone 14 Pro', 999.99,
        'The best smaller phone ever',
        (select id from product_category where name = 'Phone')),
       ('Apple iPad Air (2022)', 1050.99,
        'The best tablet for everyone',
        (select id from product_category where name = 'Tablet')),
       ('iPad 2022 (10th gen)', 700.99,
        'The best regular iPad in years',
        (select id from product_category where name = 'Tablet')),
       ('Samsung Galaxy Tab S8', 800.99,
        'The best premium Android tablet',
        (select id from product_category where name = 'Tablet'));

INSERT INTO PUBLIC.product_property (name,
                                     description,
                                     product_id)
VALUES ('Color',
        'With an all-new design, the M2 MacBook Air comes in four color options: Midnight, Space Gray, Silver, and Starlight.',
        (select id from product where name = 'Apple MacBook Air (M2)')),
       ('Camera',
        'Fortunately, the camera inside that notch is the same 1080p unit that''s in the larger Pro models, and it''s much improved over the crappy 720p camera that was in the older Air and the new 13-inch MacBook Pro',
        (select id from product where name = 'Apple MacBook Air (M2)')),
       ('Screen',
        'The MacBook Air M2 has caught up to the MacBook Pro M2 in terms of display quality, and that''s a big deal. You get a bright and colorful 13.6-inch panel (2560 x 1664 pixels) for watching videos, editing photos and everything else you want to do with no trade-offs.',
        (select id from product where name = 'Apple MacBook Air (M2)'));

INSERT INTO PUBLIC.inventory_item (quantity,
                                   product_id)
VALUES (100, (select id from product where name = 'Apple MacBook Air (M2)')),
       (100, (select id from product where name = 'MacBook Pro (14-inch and 16-inch)')),
       (100, (select id from product where name = 'HP Spectre x360 14')),
       (100, (select id from product where name = 'Samsung Galaxy S23 Ultra')),
       (100, (select id from product where name = 'iPhone 14 Pro Max')),
       (100, (select id from product where name = 'iPhone 14 Pro')),
       (100, (select id from product where name = 'Apple iPad Air (2022)')),
       (100, (select id from product where name = 'iPad 2022 (10th gen)')),
       (100, (select id from product where name = 'Samsung Galaxy Tab S8'));