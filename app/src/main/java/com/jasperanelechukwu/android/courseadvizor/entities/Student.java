package com.jasperanelechukwu.android.courseadvizor.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String firstName;

    private String lastName;

    private String matriculationNumber;

    private String phoneNumber;

    private String createdAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
