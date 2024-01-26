package ua.nure.cpp.sivenko.practice6.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    private long paymentMethodId;
    private String paymentMethodName;
}
