package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;

import lombok.Data;

@Data
public class UpdateAppointmentFormUiState {
    private String startedAt;

    private final Appointment.Status status = Appointment.Status.accepted;
}
