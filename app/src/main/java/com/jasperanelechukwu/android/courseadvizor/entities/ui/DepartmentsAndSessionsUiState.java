package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Department;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentsAndSessionsUiState {
    private List<Department> departments;

    private List<Session> sessions;

    private boolean loading;

    private String error;

    private boolean loaded;

    public DepartmentsAndSessionsUiState() {
        this(new ArrayList<>(), new ArrayList<>(), false, null, false);
    }

    public DepartmentsAndSessionsUiState(boolean loading) {
        this(new ArrayList<>(), new ArrayList<>(), loading, null, false);
    }

    public DepartmentsAndSessionsUiState(String error) {
        this(new ArrayList<>(), new ArrayList<>(), false, error, false);
    }

    public DepartmentsAndSessionsUiState(List<Department> departments, List<Session> sessions, boolean loaded) {
        this(departments, sessions, false, null, loaded);
    }
}
