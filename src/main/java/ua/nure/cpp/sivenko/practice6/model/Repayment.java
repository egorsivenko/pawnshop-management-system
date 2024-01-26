package ua.nure.cpp.sivenko.practice6.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repayment {
    private long repaymentId;

    private long transactionId;
    private long paymentMethodId;
    private LocalDateTime repaymentDate;
}
