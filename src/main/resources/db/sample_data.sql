START TRANSACTION;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE categories;
INSERT INTO categories (category_id, category_name) VALUES
  (1, 'Shoes'),
  (2, 'Sports'),
  (3, 'T-shirts'),
  (4, 'Pants'),
  (5, 'Skirts'),
  (6, 'Dresses');

TRUNCATE TABLE vendors;
INSERT INTO vendors (vendor_id, vendor_name) VALUES
  (1, 'Mega Sport'),
  (2, 'Zara'),
  (3, 'H&M'),
  (4, 'Castro'),
  (5, 'Pull & Bear'),
  (6, 'Hangar');

TRUNCATE TABLE vendor_categories;
INSERT INTO vendor_categories (vendor_id, category_id) VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (2, 4),
  (2, 5),
  (2, 6),
  (3, 3),
  (3, 4),
  (4, 1),
  (4, 3),
  (4, 6),
  (5, 5),
  (5, 6),
  (6, 1),
  (6, 2),
  (6, 3);

TRUNCATE TABLE sales;
INSERT INTO sales (sale_id, vendor_id, sale_title, sale_start, sale_end) VALUES
  (1, 1, '15% off on all running shoes', '2014-01-01', '2014-01-10'),
  (2, 1, '10% off on dry-fit shirts', '2014-01-01', '2014-01-10'),
  (3, 1, '2+1 on accessories', '2014-01-01', '2014-01-10'),
  (4, 2, '1+1 on ties', '2014-01-01', '2014-01-10'),
  (5, 2, 'Elegant shoes 5% off', '2014-01-01', '2014-01-10'),
  (6, 2, 'Women\'s bags discounts', '2014-01-01', '2014-01-10'),
  (7, 3, '10% on pants', '2014-01-01', '2014-01-10'),
  (8, 3, '20% on jackets', '2014-01-01', '2014-01-10'),
  (9, 3, 'End of season - up to 50% discounts', '2014-01-01', '2014-01-10'),
  (10, 4, 'New sunglasses', '2014-01-01', '2014-01-10'),
  (11, 4, 'Discounts on jeans', '2014-01-01', '2014-01-10'),
  (12, 5, 'New colorful hoodies', '2014-01-01', '2014-01-10'),
  (13, 5, 'New men\'s elegant shoes', '2014-01-01', '2014-01-10'),
  (14, 6, 'Socks and underwear - 1+1', '2014-01-01', '2014-01-10'),
  (15, 6, '10% off on t-shirts', '2014-01-01', '2014-01-10'),
  (16, 6, '15% off on jeans', '2014-01-01', '2014-01-10');

SET FOREIGN_KEY_CHECKS = 1;
COMMIT;
