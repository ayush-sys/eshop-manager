-- Default Product Catalogs
INSERT INTO product_catalog_details (catalog_name, description)
VALUES
('Smartphones', 'Latest smartphones and accessories'),
('Appliances', 'Home and kitchen appliances'),
('Furniture', 'Home and office furniture'),
('Laptops', 'Personal and business laptops'),
('Wearables', 'Smartwatches, fitness bands, and accessories'),
('Audio', 'Speakers, headphones, and soundbars');

-- Default Products Details (linked directly to catalog_id)
-- Default catalog IDs are assigned sequentially in the same order as above:
-- Smartphones = 1, Appliances = 2, Furniture = 3, Laptops = 4, Wearables = 5, Audio = 6

INSERT INTO product_details
(product_name, sku, maker_name, model_number, price, discount_percent, quantity_in_stock, in_stock, description, catalog_id)
VALUES
('iPhone 15 Pro', 'IPH15PRO-BLK-256', 'Apple', 'A2849', 139999.00, 5.00, 20, TRUE, 'Apple iPhone 15 Pro with A17 Bionic chip', 1),
('Samsung Galaxy S24', 'SAMS24-128', 'Samsung', 'SM-S921B', 129999.00, 3.00, 25, TRUE, 'Samsung Galaxy S24 with AI-powered camera', 1),
('LG Smart Refrigerator', 'LG-FRZ-500L', 'LG', 'GL-T502FPZU', 75999.00, 10.00, 15, TRUE, '500L Smart Refrigerator with inverter compressor', 2),
('Sony WH-1000XM5 Headphones', 'SONY-WH1000XM5-BLK', 'Sony', 'WH1000XM5', 29999.00, 7.00, 50, TRUE, 'Noise-cancelling over-ear headphones with LDAC support', 6),
('Dell XPS 15', 'DELL-XPS15-9530', 'Dell', '9530', 184999.00, 8.00, 10, TRUE, 'Dell XPS 15 with Intel i9 and NVIDIA RTX 4070', 4),
('Apple Watch Series 9', 'APL-WTCH9-GPS-45MM', 'Apple', 'A2982', 45999.00, 2.00, 35, TRUE, 'Apple Watch Series 9 with S9 chip and brighter display', 5),
('IKEA Office Chair', 'IKEA-CHR-OF001', 'IKEA', 'OF001', 8999.00, 12.00, 30, TRUE, 'Ergonomic office chair with adjustable height', 3),
('Boat Stone 1350 Speaker', 'BOAT-SPK-1350', 'Boat', '1350', 4999.00, 15.00, 40, TRUE, 'Portable Bluetooth speaker with deep bass', 6),
('Samsung Washing Machine', 'SAMS-WM-7KG', 'Samsung', 'WW70T4040CX', 29999.00, 6.00, 18, TRUE, '7kg Front-load washing machine with EcoBubble', 2);
