package ua.nure.cpp.sivenko.practice6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.PaymentMethodDAO;
import ua.nure.cpp.sivenko.practice6.model.PaymentMethod;

import java.sql.SQLException;
import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodDAO paymentMethodDAO;

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodDAO.getAllPaymentMethods();
    }

    public PaymentMethod getPaymentMethodById(long paymentMethodId) {
        return paymentMethodDAO.getPaymentMethodById(paymentMethodId);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        List<PaymentMethod> paymentMethods = paymentMethodDAO.getAllPaymentMethods();

        for (var method : paymentMethods) {
            if (method.getPaymentMethodName().equalsIgnoreCase(paymentMethod.getPaymentMethodName().strip())) {
                throw new SQLException("Payment Method with name '" + paymentMethod.getPaymentMethodName().strip() + "' already exists");
            }
        }
        paymentMethodDAO.addPaymentMethod(paymentMethod.getPaymentMethodName());
    }

    public void deletePaymentMethod(long paymentMethodId) throws SQLException {
        PaymentMethod paymentMethodById = paymentMethodDAO.getPaymentMethodById(paymentMethodId);
        if (paymentMethodById == null) {
            throw new SQLException("Payment Method with id '" + paymentMethodId + "' does not exists");
        }
        paymentMethodDAO.deletePaymentMethod(paymentMethodId);
    }

    public void updatePaymentMethodName(long paymentMethodId, String paymentMethodName) throws SQLException {
        List<PaymentMethod> paymentMethods = paymentMethodDAO.getAllPaymentMethods();

        for (var method : paymentMethods) {
            if (method.getPaymentMethodName().equalsIgnoreCase(paymentMethodName.strip())) {
                throw new SQLException("Payment Method with name '" + paymentMethodName.strip() + "' already exists");
            }
        }
        paymentMethodDAO.updatePaymentMethodName(paymentMethodId, paymentMethodName);
    }
}
