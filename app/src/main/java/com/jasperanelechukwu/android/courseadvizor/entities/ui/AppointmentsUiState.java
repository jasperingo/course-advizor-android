package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentsUiState {
    private List<Appointment> appointments;

    private boolean loading;

    private String error;

    private boolean loaded;

    public AppointmentsUiState() {
        this(new ArrayList<>(), false, null, false);
    }

    public AppointmentsUiState(boolean loading) {
        this(new ArrayList<>(), loading, null, false);
    }

    public AppointmentsUiState(String error) {
        this(new ArrayList<>(), false, error, false);
    }

    public AppointmentsUiState(List<Appointment> appointments, boolean loaded) {
        this(appointments, false, null, loaded);
    }
}
