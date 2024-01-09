package ua.nure.cpp.sivenko.practice6.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PawnTransaction {
    private long transactionId;
    private long customerId;
    private long itemId;
    private long pawnbrokerId;
    private BigDecimal pawnAmount;
    private int interestRate;
    private int monthlyPeriod;
    private BigDecimal repaymentAmount;
    private LocalDate pawnDate;
    private LocalDate expirationDate;
    private TransactionStatus transactionStatus;

    public enum TransactionStatus {
        ACTIVE, REPAID, EXPIRED;

        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
