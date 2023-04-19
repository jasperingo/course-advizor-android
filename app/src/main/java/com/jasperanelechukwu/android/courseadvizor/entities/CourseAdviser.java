package com.jasperanelechukwu.android.courseadvizor.entities;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CourseAdviser {
    private long id;

    @SerializedName(value = "first_name")
    private String firstName;

    @SerializedName(value = "last_name")
    private String lastName;

    @SerializedName(value = "phone_number")
    private String phoneNumber;

    private Department department;

    private Session session;

    public String getFullName() {
        return firstName+" "+lastName;
    }
}
