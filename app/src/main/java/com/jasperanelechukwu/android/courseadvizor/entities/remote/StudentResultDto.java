package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.jasperanelechukwu.android.courseadvizor.entities.StudentResult;

import lombok.Data;

@Data
public class StudentResultDto {
    private long id;

    private StudentResult.Grade grade;

    public StudentResult toStudentResult() {
        return new StudentResult(id, grade);
    }
}
