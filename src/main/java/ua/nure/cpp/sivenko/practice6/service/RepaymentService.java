package ua.nure.cpp.sivenko.practice6.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.RepaymentDAO;
import ua.nure.cpp.sivenko.practice6.model.Repayment;

import java.sql.SQLException;
import java.util.List;

@Service
@Log
public class RepaymentService {

    @Autowired
    private RepaymentDAO repaymentDAO;

    public List<Repayment> getAllRepayments() {
        return repaymentDAO.getAllRepayments();
    }

    public Repayment getRepaymentById(long repaymentId) {
        return repaymentDAO.getRepaymentById(repaymentId);
    }

    public void addRepayment(Repayment repayment) {
        try {
            repaymentDAO.addRepayment(repayment);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
