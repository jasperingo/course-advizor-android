package com.jasperanelechukwu.android.courseadvizor.entities;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private int id;

    private String name;

    private String abbreviation;

    private String createdAt;

    @NonNull
    @Override
    public String toString() {
        return name == null ? "" : name+" ("+abbreviation+")";
    }
}
