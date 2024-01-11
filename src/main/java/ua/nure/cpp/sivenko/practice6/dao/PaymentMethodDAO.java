package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.PaymentMethod;

import java.sql.SQLException;
import java.util.List;

public interface PaymentMethodDAO {
    PaymentMethod getPaymentMethodById(long paymentMethodId);

    List<PaymentMethod> getAllPaymentMethods();

    void addPaymentMethod(String paymentMethodName) throws SQLException;

    void updatePaymentMethodName(long paymentMethodId, String paymentMethodName) throws SQLException;

    void deletePaymentMethod(long paymentMethodId);
}
