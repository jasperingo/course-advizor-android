package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Embedded;

import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;

import lombok.Data;

@Data
public class AppointmentEntityAndStudentEntity {
    @Embedded(prefix = "appointment_")
    private AppointmentEntity appointment;

    @Embedded(prefix = "student_")
    private StudentEntity student;

    public Appointment toAppointment() {
        return appointment.toAppointment(student.toStudent());
    }
}
