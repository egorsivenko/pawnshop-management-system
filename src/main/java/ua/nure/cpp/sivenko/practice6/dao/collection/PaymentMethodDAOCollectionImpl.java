package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.PaymentMethodDAO;
import ua.nure.cpp.sivenko.practice6.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentMethodDAOCollectionImpl implements PaymentMethodDAO {
    private final List<PaymentMethod> paymentMethods = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public PaymentMethod getPaymentMethodById(long paymentMethodId) {
        return paymentMethods.stream()
                .filter(paymentMethod -> paymentMethod.getPaymentMethodId() == paymentMethodId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethods;
    }

    @Override
    public void addPaymentMethod(String paymentMethodName) {
        paymentMethods.add(new PaymentMethod(id.getAndIncrement(), paymentMethodName));
    }

    @Override
    public void updatePaymentMethodName(long paymentMethodId, String paymentMethodName) {
        paymentMethods.stream()
                .filter(paymentMethod -> paymentMethod.getPaymentMethodId() == paymentMethodId)
                .findFirst()
                .ifPresent(paymentMethod -> paymentMethod.setPaymentMethodName(paymentMethodName));
    }

    @Override
    public void deletePaymentMethod(long paymentMethodId) {
        paymentMethods.removeIf(paymentMethod -> paymentMethod.getPaymentMethodId() == paymentMethodId);
    }
}
