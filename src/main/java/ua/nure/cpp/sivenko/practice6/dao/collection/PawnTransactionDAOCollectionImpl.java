package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.PawnTransactionDAO;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PawnTransactionDAOCollectionImpl implements PawnTransactionDAO {
    private final List<PawnTransaction> pawnTransactions = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public PawnTransactionDAOCollectionImpl() {
        addPawnTransaction(new PawnTransaction(1, 1, 4, 8, BigDecimal.valueOf(426),
                20, 1, BigDecimal.valueOf(511.20), LocalDate.now(),
                LocalDate.now().plusMonths(1), TransactionStatus.ACTIVE));
    }

    @Override
    public PawnTransaction getPawnTransactionById(long transactionId) {
        return pawnTransactions.stream()
                .filter(pawnTransaction -> pawnTransaction.getTransactionId() == transactionId)
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

    @Override
    public void deletePawnTransaction(long transactionId) {
        pawnTransactions.remove((int) transactionId - 1);
    }
}
