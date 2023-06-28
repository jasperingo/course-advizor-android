package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentDto {
    private long id;

    private Appointment.Status status;

    @SerializedName("started_at")
    private LocalDateTime startedAt;

    @SerializedName("created_at")
    private LocalDateTime createdAt;

    private StudentDto student;

    public AppointmentEntity toAppointmentEntity() {
        return new AppointmentEntity(id, student.getId(), status, startedAt, createdAt);
    }
}
