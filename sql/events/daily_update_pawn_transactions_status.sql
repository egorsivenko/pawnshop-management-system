DROP EVENT IF EXISTS daily_update_pawn_transactions_status;

DELIMITER $$
CREATE EVENT daily_update_pawn_transactions_status
ON SCHEDULE EVERY 1 DAY
DO BEGIN
    UPDATE pawn_transactions
    SET transaction_status = 'Expired'
    WHERE expiration_date <= CURDATE() AND transaction_status = 'Active';
END $$

DELIMITER ;
