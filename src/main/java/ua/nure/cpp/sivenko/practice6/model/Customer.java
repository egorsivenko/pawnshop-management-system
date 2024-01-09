package ua.nure.cpp.sivenko.practice6.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private long customerId;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;
}
