INSERT INTO inventory.file_metadata (uid,
                                     file_name,
                                     file_extension,
                                     file_key,
                                     s3_url)
VALUES ('jdf2545a-f869-11ed-b67e-0242ac120002', 'sorry_not_found.jpg', 'jpg', 'sorry_not_found.jpg',
        'https://marius-image-storage.s3.eu-north-1.amazonaws.com/sorry_not_found.jpg');

INSERT INTO inventory.product_file (uid,
                                    product_id,
                                    file_id)
VALUES ('pdf2545a-f869-11ed-b67e-0242ac120002', 1, 1);