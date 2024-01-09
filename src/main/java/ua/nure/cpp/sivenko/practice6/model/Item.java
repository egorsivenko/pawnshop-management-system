package ua.nure.cpp.sivenko.practice6.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private long itemId;
    private String itemName;
    private long itemCategory;
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
