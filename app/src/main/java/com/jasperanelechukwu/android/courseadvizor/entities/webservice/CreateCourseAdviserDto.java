package com.jasperanelechukwu.android.courseadvizor.entities.webservice;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseAdviserDto {
    private String pin;

    @SerializedName(value = "first_name")
    private String firstName;

    @SerializedName(value = "last_name")
    private String lastName;

    @SerializedName(value = "phone_number")
    private String phoneNumber;

    @SerializedName(value = "department_id")
    private long departmentId;

    @SerializedName(value = "session_id")
    private long sessionId;
}
