DROP TRIGGER IF EXISTS pawn_transactions_before_insert;

DELIMITER $$
CREATE TRIGGER pawn_transactions_before_insert
    BEFORE INSERT ON pawn_transactions
    FOR EACH ROW
BEGIN
    DECLARE item_market_price_max DECIMAL(9, 2);
    DECLARE item_market_price_min DECIMAL(9, 2);
    DECLARE item_category VARCHAR(50);
    DECLARE item_category_name VARCHAR(255);

    SELECT i.market_price_max, i.market_price_min, i.item_category, ic.item_category_name
    INTO item_market_price_max, item_market_price_min, item_category, item_category_name
    FROM items i
    JOIN item_categories ic ON i.item_category = ic.item_category_id
    WHERE i.item_id = NEW.item_id;

    IF NOT EXISTS (SELECT * FROM pawnbroker_specialization WHERE pawnbroker_id = NEW.pawnbroker_id AND specialization = item_category) THEN
        SET @errorMessage = CONCAT('Pawnbroker with id \'', NEW.pawnbroker_id, '\' is not specialized in the item category - ', item_category_name);
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;
    ELSEIF (NEW.pawn_amount > item_market_price_max OR NEW.pawn_amount < item_market_price_min) THEN
        SET @errorMessage = CONCAT('Invalid pawn amount value, should be between ', item_market_price_min, ' and ', item_market_price_max);
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;
    END IF;
END $$

DELIMITER ;
