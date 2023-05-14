package com.jasperanelechukwu.android.courseadvizor.entities;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentWithResult extends Student {
    private List<StudentResult> studentResults;

    public StudentWithResult(
        long id,
        String firstName,
        String lastName,
        String matriculationNumber,
        String phoneNumber,
        String createdAt,
        List<StudentResult> studentResults
    ) {
        super(id, firstName, lastName, matriculationNumber, phoneNumber, createdAt);
        this.studentResults = studentResults;
    }
}
