INSERT INTO brand (id, name) VALUES(1, 'ZARA')
INSERT INTO brand (id, name) VALUES(2, 'BERSHKA')
INSERT INTO brand (id, name) VALUES(3, 'PULL & BEAR')
INSERT INTO brand (id, name) VALUES(4, 'MASSIMO DUTTI')
INSERT INTO brand (id, name) VALUES(5, 'STRADIVARIUS')
INSERT INTO brand (id, name) VALUES(6, 'OYSHO')

INSERT INTO product (id, name) VALUES (35455, 'Product 1');
INSERT INTO product (id, name) VALUES (35456, 'Product 2');
INSERT INTO product (id, name) VALUES (35457, 'Product 3');

INSERT INTO price (id, brand_id, start_date, end_date, product_id, priority, price, currency) VALUES (1, 1, '2020-06-14T00:00:00Z', '2020-12-31T23:59:59Z', 35455, 0, 35.50, 'EUR');
INSERT INTO price (id, brand_id, start_date, end_date, product_id, priority, price, currency) VALUES (2, 1, '2020-06-14T15:00:00Z', '2020-06-14T18:30:00Z', 35455, 1, 25.45, 'EUR');
INSERT INTO price (id, brand_id, start_date, end_date, product_id, priority, price, currency) VALUES (3, 1, '2020-06-15T00:00:00Z', '2020-06-15T11:00:00Z', 35455, 2, 30.50, 'EUR');
INSERT INTO price (id, brand_id, start_date, end_date, product_id, priority, price, currency) VALUES (4, 1, '2020-06-15T16:00:00Z', '2020-12-31T23:59:59Z', 35455, 3, 38.95, 'EUR');
