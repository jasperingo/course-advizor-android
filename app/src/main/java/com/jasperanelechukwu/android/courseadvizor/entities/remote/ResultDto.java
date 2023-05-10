package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntity;

import lombok.Data;

@Data
public class ResultDto {
    private long id;

    @SerializedName("course_code")
    private String courseCode;

    private Result.Semester semester;

    @SerializedName("created_at")
    private String createdAt;

    private Session session;

    public ResultEntity toResultEntity() {
        return new ResultEntity(id, session.getId(), courseCode, semester, createdAt);
    }
}
