package ua.nure.cpp.sivenko.practice6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.PawnTransactionDAO;
import ua.nure.cpp.sivenko.practice6.dao.RepaymentDAO;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice6.model.Repayment;

import java.sql.SQLException;
import java.util.List;

@Service
public class RepaymentService {

    @Autowired
    private RepaymentDAO repaymentDAO;

    @Autowired
    private PawnTransactionDAO pawnTransactionDAO;

    public List<Repayment> getAllRepayments() {
        return repaymentDAO.getAllRepayments();
    }

    public Repayment getRepaymentById(long repaymentId) {
        return repaymentDAO.getRepaymentById(repaymentId);
    }

    public void addRepayment(Repayment repayment) throws SQLException {
        long transactionId = repayment.getTransactionId();
        PawnTransaction pawnTransaction = pawnTransactionDAO.getPawnTransactionById(transactionId);

        if (pawnTransaction == null) {
            throw new SQLException("Pawn Transaction with id '" + transactionId + "' does not exists");
        } else if (pawnTransaction.getTransactionStatus() == PawnTransaction.TransactionStatus.REPAID) {
            throw new SQLException("Pawn Transaction with id '" + transactionId + "' is already repaid");
        }
        repaymentDAO.addRepayment(repayment);
    }
}
