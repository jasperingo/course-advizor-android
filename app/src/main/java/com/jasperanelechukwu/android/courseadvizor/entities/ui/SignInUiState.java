package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInUiState {
    private boolean loading;

    private String error;

    @StringRes private int formError;

    @StringRes private int pinError;

    @StringRes private int phoneNumberError;

    public SignInUiState(boolean loading) {
        this.loading = loading;
    }

    public SignInUiState(String error) {
        this.error = error;
    }

    public SignInUiState(
        InvalidFormException.InputError formError,
        InvalidFormException.InputError pinError,
        InvalidFormException.InputError phoneNumberError
    ) {
        if (formError != null) this.formError = formError.forInput();

        if (pinError != null) this.pinError = pinError.forInput();

        if (phoneNumberError != null) this.phoneNumberError = phoneNumberError.forInput();
    }
}
