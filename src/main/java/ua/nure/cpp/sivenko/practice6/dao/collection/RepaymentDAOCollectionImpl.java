package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.RepaymentDAO;
import ua.nure.cpp.sivenko.practice6.model.Repayment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RepaymentDAOCollectionImpl implements RepaymentDAO {
    private final List<Repayment> repayments = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public Repayment getRepaymentById(long repaymentId) {
        return repayments.stream()
                .filter(repayment -> repayment.getRepaymentId() == repaymentId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Repayment getRepaymentByTransactionId(long transactionId) {
        return repayments.stream()
                .filter(repayment -> repayment.getTransactionId() == transactionId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Repayment> getRepaymentsByPaymentMethod(long paymentMethodId) {
        return repayments.stream()
                .filter(repayment -> repayment.getPaymentMethodId() == paymentMethodId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Repayment> getAllRepayments() {
        return repayments;
    }

    @Override
    public void addRepayment(Repayment repayment) {
        repayment.setRepaymentId(id.getAndIncrement());
        repayments.add(repayment);
    }
}
