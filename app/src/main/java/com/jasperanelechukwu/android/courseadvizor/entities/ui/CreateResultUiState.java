package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateResultUiState {
    private Result result;

    private boolean loading;

    private String error;

    @StringRes
    private int formError;

    @StringRes private int courseCodeError;

    @StringRes private int semesterError;

    @StringRes private int sessionError;

    public CreateResultUiState(Result result) {
        this.result = result;
    }

    public CreateResultUiState(boolean loading) {
        this.loading = loading;
    }

    public CreateResultUiState(String error) {
        this.error = error;
    }

    public CreateResultUiState(
            InvalidFormException.InputError formError,
            InvalidFormException.InputError courseCodeError,
            InvalidFormException.InputError semesterError,
            InvalidFormException.InputError sessionError
    ) {
        if (formError != null) this.formError = formError.forInput();

        if (courseCodeError != null) this.courseCodeError = courseCodeError.forInput();

        if (semesterError != null) this.semesterError = semesterError.forInput();

        if (sessionError != null) this.sessionError = sessionError.forInput();
    }
}
