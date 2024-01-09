package ua.nure.cpp.sivenko.practice6.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Repayment {
    private long repaymentId;
    private long transactionId;
    private long paymentMethod;
    private LocalDateTime repaymentDate;
}
