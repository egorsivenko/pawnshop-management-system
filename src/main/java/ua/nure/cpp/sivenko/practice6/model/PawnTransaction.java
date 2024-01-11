package ua.nure.cpp.sivenko.practice6.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PawnTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
