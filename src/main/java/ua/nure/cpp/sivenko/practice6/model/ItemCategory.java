package ua.nure.cpp.sivenko.practice6.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory {
    private long itemCategoryId;
    private String itemCategoryName;

    private List<Pawnbroker> activePawnbrokers;
}
