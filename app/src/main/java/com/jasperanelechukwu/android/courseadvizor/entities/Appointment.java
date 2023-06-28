package com.jasperanelechukwu.android.courseadvizor.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    public enum Status {
        pending, accepted, declined
    }

    private long id;

    private Status status;

    private LocalDateTime startedAt;

    private LocalDateTime createdAt;

    private Student student;
}
