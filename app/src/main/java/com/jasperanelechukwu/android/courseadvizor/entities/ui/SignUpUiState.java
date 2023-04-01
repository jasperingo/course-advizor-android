package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUiState {
    private boolean loading;

    private String error;

    @StringRes private int formError;

    @StringRes private int pinError;

    @StringRes private int firstNameError;

    @StringRes private int lastNameError;

    @StringRes private int phoneNumberError;

    @StringRes private int departmentError;

    @StringRes private int sessionError;

    public SignUpUiState(boolean loading) {
        this.loading = loading;
    }

    public SignUpUiState(String error) {
        this.error = error;
    }

    public SignUpUiState(
        InvalidFormException.InputError formError,
        InvalidFormException.InputError pinError,
        InvalidFormException.InputError firstNameError,
        InvalidFormException.InputError lastNameError,
        InvalidFormException.InputError phoneNumberError,
        InvalidFormException.InputError departmentError,
        InvalidFormException.InputError sessionError
    ) {
        if (formError != null) this.formError = formError.forInput();

        if (pinError != null) this.pinError = pinError.forInput();

        if (firstNameError != null) this.firstNameError = firstNameError.forInput();

        if (lastNameError != null) this.lastNameError = lastNameError.forInput();

        if (phoneNumberError != null) this.phoneNumberError = phoneNumberError.forInput();

        if (departmentError != null) this.departmentError = departmentError.forInput();

        if (sessionError != null) this.sessionError = sessionError.forInput();
    }
}
