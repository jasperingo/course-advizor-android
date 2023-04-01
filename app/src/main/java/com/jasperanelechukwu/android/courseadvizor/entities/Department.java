package com.jasperanelechukwu.android.courseadvizor.entities;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Department {
    private int id;
    private String name;
    private String abbreviation;

    @SerializedName(value = "created_at")
    private String createdAt;

    @NonNull
    public String toString() {
        return name == null ? "" : name+" ("+abbreviation+")";
    }
}
