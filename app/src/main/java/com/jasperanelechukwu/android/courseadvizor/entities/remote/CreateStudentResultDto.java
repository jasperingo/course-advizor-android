package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentResultDto {
    @SerializedName(value = "student_id")
    private long studentId;

    @SerializedName(value = "result_id")
    private long resultId;

    private String grade;
}
