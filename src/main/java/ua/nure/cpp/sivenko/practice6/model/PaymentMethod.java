package ua.nure.cpp.sivenko.practice6.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    private long paymentMethodId;
    private String paymentMethodName;
}
