DROP DATABASE IF EXISTS pawnshop;
CREATE DATABASE pawnshop;
USE pawnshop;

CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    contact_number VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE pawnbrokers (
    pawnbroker_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birthdate DATE NOT NULL,
    contact_number VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE item_categories (
    item_category_id INT AUTO_INCREMENT PRIMARY KEY,
    item_category_name VARCHAR(255) NOT NULL UNIQUE
);
INSERT INTO item_categories (item_category_name)
VALUES  ('Jewelry'), ('Electronics'), ('Collectibles'), ('Antiques'), ('Musical Instruments'),
        ('Art and Memorabilia'), ('Clothing'), ('Sporting goods'), ('Books'), ('Furniture'),
        ('Watches'), ('Vehicle'), ('Tools and Equipment'), ('Firearms'), ('Coins and Currency');

CREATE TABLE items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(50) NOT NULL,
    item_category INT,
    appraised_value DECIMAL(9, 2) NOT NULL,
    market_price_max DECIMAL(9, 2) GENERATED ALWAYS AS (appraised_value * 0.6) STORED,
    market_price_min DECIMAL(9, 2) GENERATED ALWAYS AS (appraised_value * 0.25) STORED,
    item_status ENUM('Pawned', 'Redeemed', 'Pawnshop property') NOT NULL DEFAULT 'Pawned',
    FOREIGN KEY (item_category) REFERENCES item_categories(item_category_id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE pawnbroker_specialization (
    pawnbroker_id INT,
    specialization INT,
    PRIMARY KEY (pawnbroker_id, specialization),
    FOREIGN KEY (pawnbroker_id) REFERENCES pawnbrokers(pawnbroker_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (specialization) REFERENCES item_categories(item_category_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE pawn_transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    item_id INT UNIQUE,
    pawnbroker_id INT,
    pawn_amount DECIMAL(9, 2) NOT NULL,
    interest_rate TINYINT NOT NULL DEFAULT 20,
    monthly_period TINYINT NOT NULL DEFAULT 1,
    repayment_amount DECIMAL(9, 2) GENERATED ALWAYS AS (pawn_amount + (pawn_amount * interest_rate / 100 * monthly_period)) STORED,
    pawn_date DATE NOT NULL DEFAULT (CURDATE()),
    expiration_date DATE GENERATED ALWAYS AS (DATE_ADD(pawn_date, INTERVAL monthly_period MONTH)) STORED,
    transaction_status ENUM('Active', 'Repaid', 'Expired') NOT NULL DEFAULT 'Active',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (pawnbroker_id) REFERENCES pawnbrokers(pawnbroker_id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE payment_methods (
    payment_method_id INT AUTO_INCREMENT PRIMARY KEY,
    payment_method_name VARCHAR(255) NOT NULL UNIQUE
);
INSERT INTO payment_methods (payment_method_name)
VALUES  ('Cash'), ('Credit Card'), ('Bank transfer'), ('PayPal'), ('Contactless NFC payment');

CREATE TABLE repayments (
    repayment_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT NOT NULL UNIQUE,
    payment_method INT DEFAULT 1,
    repayment_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES pawn_transactions(transaction_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (payment_method) REFERENCES payment_methods(payment_method_id) ON UPDATE CASCADE ON DELETE SET NULL
);
