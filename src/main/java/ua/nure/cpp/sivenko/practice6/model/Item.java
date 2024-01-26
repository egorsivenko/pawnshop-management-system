package ua.nure.cpp.sivenko.practice6.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
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
