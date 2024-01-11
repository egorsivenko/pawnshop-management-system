package ua.nure.cpp.sivenko.practice6.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pawnbroker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pawnbrokerId;

    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String contactNumber;
    private String email;
    private String address;

    @ManyToMany
    private List<ItemCategory> specializations;
}
