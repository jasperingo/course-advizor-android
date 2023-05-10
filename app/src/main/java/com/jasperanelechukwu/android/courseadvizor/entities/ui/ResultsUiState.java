package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultsUiState {
    private List<Result> results;

    private boolean loading;

    private String error;

    private boolean loaded;

    public ResultsUiState() {
        this(new ArrayList<>(), false, null, false);
    }

    public ResultsUiState(boolean loading) {
        this(new ArrayList<>(), loading, null, false);
    }

    public ResultsUiState(String error) {
        this(new ArrayList<>(), false, error, false);
    }

    public ResultsUiState(List<Result> results, boolean loaded) {
        this(results, false, null, loaded);
    }
}
