package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateResultDto {
    @SerializedName("course_code")
    private String courseCode;

    private Result.Semester semester;

    @SerializedName("session_id")
    private long sessionId;
}
