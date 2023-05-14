package com.jasperanelechukwu.android.courseadvizor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResult {
    public enum Grade {
        A, B, C, D, E, F
    }

    private long id;

    private Grade grade;
}
