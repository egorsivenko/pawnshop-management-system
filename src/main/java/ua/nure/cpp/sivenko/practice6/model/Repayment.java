package ua.nure.cpp.sivenko.practice6.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Repayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long repaymentId;

    private long transactionId;
    private long paymentMethod;
    private LocalDateTime repaymentDate;
}
