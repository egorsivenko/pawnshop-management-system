package ua.nure.cpp.sivenko.practice6.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.PawnTransactionDAO;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;

import java.sql.SQLException;
import java.util.List;

@Service
@Log
public class PawnTransactionService {

    @Autowired
    private PawnTransactionDAO pawnTransactionDAO;

    public List<PawnTransaction> getAllPawnTransactions() {
        return pawnTransactionDAO.getAllPawnTransactions();
    }

    public PawnTransaction getPawnTransactionById(long pawnTransactionId) {
        return pawnTransactionDAO.getPawnTransactionById(pawnTransactionId);
    }

    public void addPawnTransaction(PawnTransaction pawnTransaction) {
        try {
            pawnTransactionDAO.addPawnTransaction(pawnTransaction);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
