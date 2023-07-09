package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.entities.Report;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReportUiState {
    private boolean success;

    private boolean loading;

    private String error;

    @StringRes
    private int formError;

    public UpdateReportUiState(Report report) {
        this.success = report != null;
    }

    public UpdateReportUiState(boolean loading) {
        this.loading = loading;
    }

    public UpdateReportUiState(String error) {
        this.error = error;
    }

    public UpdateReportUiState(InvalidFormException.InputError formError) {
        if (formError != null) {
            this.formError = formError.forInput();
        }
    }
}
