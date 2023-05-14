package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultUiState {
    private Result result;

    private boolean loading;

    private String error;

    public ResultUiState() {
        this(null, false, null);
    }

    public ResultUiState(boolean loading) {
        this(null, loading, null);
    }

    public ResultUiState(String error) {
        this(null, false, error);
    }

    public ResultUiState(Result result) {
        this(result, false, null);
    }
}
