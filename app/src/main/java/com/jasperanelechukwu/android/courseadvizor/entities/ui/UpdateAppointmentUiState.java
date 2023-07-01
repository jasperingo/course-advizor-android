package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentUiState {
    private boolean success;

    private boolean loading;

    private String error;

    @StringRes
    private int formError;

    public UpdateAppointmentUiState(Appointment appointment) {
        this.success = appointment != null;
    }

    public UpdateAppointmentUiState(boolean loading) {
        this.loading = loading;
    }

    public UpdateAppointmentUiState(String error) {
        this.error = error;
    }

    public UpdateAppointmentUiState(InvalidFormException.InputError formError) {
        if (formError != null) {
            this.formError = formError.forInput();
        }
    }
}
