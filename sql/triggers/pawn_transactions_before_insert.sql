DROP TRIGGER IF EXISTS pawn_transactions_before_insert;

DELIMITER $$
CREATE TRIGGER pawn_transactions_before_insert
    BEFORE INSERT ON pawn_transactions
    FOR EACH ROW
BEGIN
    DECLARE item_market_price_max DECIMAL(9, 2);
    DECLARE item_market_price_min DECIMAL(9, 2);
    DECLARE item_category VARCHAR(50);

    SELECT i.market_price_max, i.market_price_min, i.item_category
    INTO item_market_price_max, item_market_price_min, item_category
    FROM items i WHERE i.item_id = NEW.item_id;

    IF NOT EXISTS (SELECT * FROM pawnbroker_specialization WHERE pawnbroker_id = NEW.pawnbroker_id AND specialization = item_category) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Pawnbroker is not specialized in this item category.';
    ELSEIF (NEW.pawn_amount > item_market_price_max OR NEW.pawn_amount < item_market_price_min) THEN
        SIGNAL SQLSTATE '22003' SET MESSAGE_TEXT = 'Invalid pawn amount value.';
    END IF;
END $$

DELIMITER ;
