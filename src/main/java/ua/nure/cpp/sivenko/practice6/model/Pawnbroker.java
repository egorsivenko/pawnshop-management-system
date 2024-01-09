package ua.nure.cpp.sivenko.practice6.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pawnbroker {
    private long pawnbrokerId;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String contactNumber;
    private String email;
    private String address;
    private List<ItemCategory> specializations;
}
