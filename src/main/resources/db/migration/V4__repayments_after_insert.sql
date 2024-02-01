DELIMITER $$

CREATE TRIGGER repayments_after_insert
    AFTER INSERT ON repayments
    FOR EACH ROW
BEGIN
    UPDATE pawn_transactions
    SET transaction_status = 'Repaid'
    WHERE transaction_id = NEW.transaction_id AND transaction_status = 'Active';
END $$

DELIMITER ;
