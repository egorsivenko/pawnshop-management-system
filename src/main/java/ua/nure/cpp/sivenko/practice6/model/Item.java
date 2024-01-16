package ua.nure.cpp.sivenko.practice6.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    private String itemName;
    private long itemCategoryId;
    private BigDecimal appraisedValue;
    private BigDecimal marketPriceMax;
    private BigDecimal marketPriceMin;
    private ItemStatus itemStatus;

    public enum ItemStatus {
        PAWNED, REDEEMED, PAWNSHOP_PROPERTY;

        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
