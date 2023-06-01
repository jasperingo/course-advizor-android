package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.StudentWithResult;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResultsUiState {
    private List<StudentWithResult> students;

    private boolean loading;

    private String error;

    private boolean loaded;

    private int studentsItemChanged;

    public StudentResultsUiState() {
        this(new ArrayList<>(), false, null, false, -1);
    }

    public StudentResultsUiState(boolean loading) {
        this(new ArrayList<>(), loading, null, false, -1);
    }

    public StudentResultsUiState(String error) {
        this(new ArrayList<>(), false, error, false, -1);
    }

    public StudentResultsUiState(List<StudentWithResult> students, boolean loaded) {
        this(students, false, null, loaded, -1);
    }

    public StudentResultsUiState(List<StudentWithResult> students, int studentsItemChanged) {
        this(students, false, null, true, studentsItemChanged);
    }
}
