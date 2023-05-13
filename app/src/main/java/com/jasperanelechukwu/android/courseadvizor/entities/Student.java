package com.jasperanelechukwu.android.courseadvizor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private long id;

    private String firstName;

    private String lastName;

    private String matriculationNumber;

    private String phoneNumber;

    private String createdAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
