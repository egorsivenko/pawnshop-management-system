package ua.nure.cpp.sivenko.practice6.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PawnbrokerForm {
    private long pawnbrokerId;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String contactNumber;
    private String email;
    private String address;
    private List<Long> specializationIds;
}
