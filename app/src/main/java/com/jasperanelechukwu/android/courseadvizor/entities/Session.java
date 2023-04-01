package com.jasperanelechukwu.android.courseadvizor.entities;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

import lombok.Data;

@Data
public class Session {
    private int id;

    @SerializedName(value = "started_at")
    private int startedAt;

    @SerializedName(value = "ended_at")
    private int endedAt;

    @NonNull
    public String toString() {
        return id == 0 ? "" : String.format(Locale.ENGLISH, "%d/%d", startedAt, endedAt);
    }
}
