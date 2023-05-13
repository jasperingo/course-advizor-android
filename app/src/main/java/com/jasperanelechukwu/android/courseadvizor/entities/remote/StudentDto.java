package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.local.StudentEntity;

import lombok.Data;

@Data
public class StudentDto {
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

    public StudentEntity toStudentEntity() {
        return new StudentEntity(id, firstName, lastName, matriculationNumber, phoneNumber, createdAt);
    }
}
