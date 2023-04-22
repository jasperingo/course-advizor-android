package com.jasperanelechukwu.android.courseadvizor.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("matriculation_number")
    private String matriculationNumber;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("created_at")
    private String createdAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
