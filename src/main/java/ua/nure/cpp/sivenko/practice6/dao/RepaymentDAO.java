package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.Repayment;

import java.util.List;

public interface RepaymentDAO {
    Repayment getRepaymentById(long repaymentId);

    Repayment getRepaymentByTransactionId(long transactionId);

    List<Repayment> getRepaymentsByPaymentMethod(long paymentMethodId);

    List<Repayment> getAllRepayments();

    void addRepayment(Repayment repayment);
}
