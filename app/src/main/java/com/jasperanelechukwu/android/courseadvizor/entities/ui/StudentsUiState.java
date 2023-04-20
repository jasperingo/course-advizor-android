package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentsUiState {
    private List<Student> students;

    private boolean loading;

    private String error;

    private boolean loaded;

    public StudentsUiState() {
        this(new ArrayList<>(), false, null, false);
    }

    public StudentsUiState(boolean loading) {
        this(new ArrayList<>(), loading, null, false);
    }

    public StudentsUiState(String error) {
        this(new ArrayList<>(), false, error, false);
    }

    public StudentsUiState(List<Student> students, boolean loaded) {
        this(students, false, null, loaded);
    }
}
