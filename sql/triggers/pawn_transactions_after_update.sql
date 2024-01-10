DROP TRIGGER IF EXISTS pawn_transactions_after_update;

DELIMITER $$
CREATE TRIGGER pawn_transactions_after_update
    AFTER UPDATE ON pawn_transactions
    FOR EACH ROW
BEGIN
    IF NEW.transaction_status = 'Expired' THEN
        UPDATE items
        SET item_status = 'Pawnshop property'
        WHERE item_id = NEW.item_id;
    ELSEIF NEW.transaction_status = 'Repaid' THEN
        UPDATE items
        SET item_status = 'Redeemed'
        WHERE item_id = NEW.item_id;
	END IF;
END $$

DELIMITER ;
