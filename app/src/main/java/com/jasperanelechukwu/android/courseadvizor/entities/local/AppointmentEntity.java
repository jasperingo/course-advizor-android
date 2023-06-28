package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Appointment")
public class AppointmentEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long studentId;

    private Appointment.Status status;

    private LocalDateTime startedAt;

    private LocalDateTime createdAt;

    public Appointment toAppointment(final Student student) {
        return new Appointment(id, status, startedAt, createdAt, student);
    }
}
