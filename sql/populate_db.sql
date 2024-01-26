INSERT INTO customers (first_name, last_name, contact_number, email)
VALUES  ('Kari', 'Metz', '105-441-2932', 'Kari41@hotmail.com'),
        ('Raquel', 'Green', '463-424-5422', 'Raquel.Green@yahoo.com'),
        ('Brandi', 'Braun', '418-497-5146', 'Brandi73@gmail.com'),
        ('Preston', 'Pagac', '195-470-2799', 'Preston.Pagac@gmail.com'),
        ('Roger', 'Bayer', '635-969-8879', 'Alfonso.OHara92@hotmail.com'),
        ('Mark', 'McKenzie', '452-775-5037', 'Mark.McKenzie8@hotmail.com'),
        ('Ronnie', 'Quigley', '718-541-4012', 'Ronnie_Quigley@gmail.com'),
        ('Joseph', 'Armstrong', '667-606-3337', 'Joseph38@hotmail.com'),
        ('Jonathon', 'Stoltenberg', '386-693-0039', 'Jonathon_Stoltenberg@yahoo.com'),
        ('Jerry', 'Becker', '389-844-5690', 'Jerry.Becker32@gmail.com');

INSERT INTO pawnbrokers (first_name, last_name, birthdate, contact_number, email, address)
VALUES  ('Rolando', 'Sipes', '1999-04-29', '520-609-7095', 'Rolando39@yahoo.com', '3800 Fisher Circle'),
        ('Ralph', 'Luettgen', '1989-10-05', '570-344-6285', 'Ralph.Luettgen75@gmail.com', '723 Blick Plaza'),
        ('Mercedes', 'Hand', '1987-10-06', '702-133-7575', 'Mercedes12@gmail.com', '982 Ezra Knolls'),
        ('Marty', 'Streich', '2003-06-11', '695-164-6754', 'Sherry1@yahoo.com', '263 Domingo Mill'),
        ('Carrie', 'Volkman', '1997-11-07', '920-966-5598', 'Carrie_Volkman58@yahoo.com', '551 Estel Trace'),
        ('Eugene', 'Kilback', '1985-06-18', '286-240-8476', 'Eugene_Kilback23@yahoo.com', '3389 Hertha Green'),
        ('Judy', 'Waelchi', '1988-10-21', '815-323-0897', 'Judy_Waelchi@hotmail.com', '91675 Hamill Prairie'),
        ('Lori', 'Mitchell', '1997-12-18', '581-893-6161', 'Lori_Mitchell@gmail.com', '45297 Jones Glens'),
        ('Anthony', 'Bins', '2002-01-11', '135-421-8092', 'Anthony_Bins88@hotmail.com', '91137 Reva Tunnel'),
        ('Justin', 'Kessler', '2003-02-28', '781-250-5425', 'Justin_Kessler@hotmail.com', '68973 Stoltenberg Mount');
        
INSERT INTO pawnbroker_specialization
VALUES  (1, 3), (1, 5), (1, 6), (1, 12),
        (2, 2), (2, 4), (2, 11),
        (3, 1), (3, 13), (3, 14),
        (4, 6), (4, 8), (4, 12), (4, 13),
        (5, 1), (5, 2), (5, 11),
        (6, 3), (6, 9), (6, 10), (6, 11), (6, 13),
        (7, 3), (7, 14), (7, 15),
        (8, 2), (8, 4), (8, 7), (8, 10), (8, 11), (8, 12),
        (9, 5), (9, 8), (9, 9), (9, 12),
        (10, 2), (10, 5), (10, 11);

INSERT INTO items (item_name, item_category, appraised_value)
VALUES  ('Electronic Steel Fish', 11, '810.00'),
        ('Handmade Soft Fish', 4, '527.00'),
        ('Unbranded Concrete Chips', 10, '851.00'),
        ('Sleek Fresh Chair', 12, '774.00'),
        ('Electronic Cotton Pants', 1, '678.00'),
        ('Refined Metal Cheese', 3, '652.00'),
        ('Handcrafted Frozen Tuna', 5, '784.00'),
        ('Unbranded Rubber Chicken', 7, '236.00'),
        ('Generic Concrete Bacon', 13, '197.00'),
        ('Rustic Frozen Keyboard', 8, '220.00');

INSERT INTO pawn_transactions (customer_id, item_id, pawnbroker_id, pawn_amount)
VALUES  (1, 4, 8, '426.00'),
        (5, 10, 4, '116.00'),
        (8, 9, 6, '114.00'),
        (10, 6, 1, '376.00'),
        (3, 4, 9, '461.00'),
        (8, 2, 2, '140.00'),
        (3, 1, 10, '390.00'),
        (3, 8, 8, '132.00'),
        (1, 6, 1, '353.00'),
        (5, 3, 6, '417.00');
