package ua.nure.cpp.sivenko.practice6.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.PaymentMethodDAO;
import ua.nure.cpp.sivenko.practice6.model.PaymentMethod;

import java.sql.SQLException;
import java.util.List;

@Service
@Log
public class PaymentMethodService {

    @Autowired
    private PaymentMethodDAO paymentMethodDAO;

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodDAO.getAllPaymentMethods();
    }

    public PaymentMethod getPaymentMethodById(long paymentMethodId) {
        return paymentMethodDAO.getPaymentMethodById(paymentMethodId);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        try {
            paymentMethodDAO.addPaymentMethod(paymentMethod.getPaymentMethodName());
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void deletePaymentMethod(long paymentMethodId) throws SQLException {
        PaymentMethod paymentMethodById = paymentMethodDAO.getPaymentMethodById(paymentMethodId);
        if (paymentMethodById == null) {
            throw new SQLException("Payment Method with id '" + paymentMethodId + "' does not exists");
        }
        paymentMethodDAO.deletePaymentMethod(paymentMethodId);
    }

    public void updatePaymentMethodName(long paymentMethodId, String paymentMethodName) {
        try {
            paymentMethodDAO.updatePaymentMethodName(paymentMethodId, paymentMethodName);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
