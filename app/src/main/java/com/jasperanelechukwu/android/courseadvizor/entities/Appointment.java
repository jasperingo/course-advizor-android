package com.jasperanelechukwu.android.courseadvizor.entities;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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

    @Nullable
    private LocalDateTime startedAt;

    private LocalDateTime createdAt;

    private Student student;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean hasEnded() {
        if (startedAt == null) {
            return false;
        }

        return startedAt.isBefore(LocalDateTime.now());
    }
}
