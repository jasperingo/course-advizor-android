package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Report;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportsUiState {
    private List<Report> reports;

    private boolean loading;

    private String error;

    private boolean loaded;

    public ReportsUiState() {
        this(new ArrayList<>(), false, null, false);
    }

    public ReportsUiState(boolean loading) {
        this(new ArrayList<>(), loading, null, false);
    }

    public ReportsUiState(String error) {
        this(new ArrayList<>(), false, error, false);
    }

    public ReportsUiState(List<Report> reports, boolean loaded) {
        this(reports, false, null, loaded);
    }
}
