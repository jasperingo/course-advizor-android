package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReportDto {
    private long id;

    @SerializedName("record_url")
    private String recordUrl;

    private String reply;

    @SerializedName("created_at")
    private LocalDateTime createdAt;

    private StudentDto student;

    public ReportEntity toReportEntity() {
        return new ReportEntity(id, student.getId(), recordUrl, reply, createdAt);
    }
}
