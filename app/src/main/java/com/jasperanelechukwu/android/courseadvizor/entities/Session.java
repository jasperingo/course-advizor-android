package com.jasperanelechukwu.android.courseadvizor.entities;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private long id;

    private int startedAt;

    private int endedAt;

    @NonNull
    @Override
    public String toString() {
        return id == 0 ? "" : startedAt+"/"+endedAt;
    }
}
