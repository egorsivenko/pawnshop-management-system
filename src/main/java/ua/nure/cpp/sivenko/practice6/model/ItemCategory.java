package ua.nure.cpp.sivenko.practice6.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemCategoryId;
    private String itemCategoryName;

    @ManyToMany
    private List<Pawnbroker> activePawnbrokers;
}
