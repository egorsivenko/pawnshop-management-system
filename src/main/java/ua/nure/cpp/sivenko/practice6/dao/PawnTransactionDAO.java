package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction.TransactionStatus;

import java.util.List;

public interface PawnTransactionDAO {
    PawnTransaction getPawnTransactionById(long transactionId);

    List<PawnTransaction> getPawnTransactionsByCustomerId(long customerId);

    List<PawnTransaction> getPawnTransactionsByStatus(TransactionStatus transactionStatus);

    List<PawnTransaction> getAllPawnTransactions();

    void addPawnTransaction(PawnTransaction pawnTransaction);

    void deletePawnTransaction(long transactionId);
}
