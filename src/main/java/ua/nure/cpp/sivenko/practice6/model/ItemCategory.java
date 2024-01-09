package ua.nure.cpp.sivenko.practice6.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory {
    private long itemCategoryId;
    private String itemCategoryName;
    private List<Pawnbroker> activePawnbrokers;
}
