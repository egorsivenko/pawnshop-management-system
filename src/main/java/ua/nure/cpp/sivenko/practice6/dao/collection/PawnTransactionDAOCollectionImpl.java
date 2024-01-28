package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.PawnTransactionDAO;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PawnTransactionDAOCollectionImpl implements PawnTransactionDAO {
    private final List<PawnTransaction> pawnTransactions = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public PawnTransaction getPawnTransactionById(long pawnTransactionId) {
        return pawnTransactions.stream()
                .filter(pawnTransaction -> pawnTransaction.getTransactionId() == pawnTransactionId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public PawnTransaction getPawnTransactionByItemId(long itemId) {
        return pawnTransactions.stream()
                .filter(pawnTransaction -> pawnTransaction.getItemId() == itemId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<PawnTransaction> getPawnTransactionsByCustomerId(long customerId) {
        return pawnTransactions.stream()
                .filter(pawnTransaction -> pawnTransaction.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<PawnTransaction> getPawnTransactionsByStatus(TransactionStatus transactionStatus) {
        return pawnTransactions.stream()
                .filter(pawnTransaction -> pawnTransaction.getTransactionStatus() == transactionStatus)
                .collect(Collectors.toList());
    }

    @Override
    public List<PawnTransaction> getAllPawnTransactions() {
        return pawnTransactions;
    }

    @Override
    public void addPawnTransaction(PawnTransaction pawnTransaction) {
        pawnTransaction.setTransactionId(id.getAndIncrement());
        pawnTransactions.add(pawnTransaction);
    }
}
