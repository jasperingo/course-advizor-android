package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.local.SessionEntity;

import lombok.Data;

@Data
public class SessionDto {
    private long id;

    @SerializedName(value = "started_at")
    private int startedAt;

    @SerializedName(value = "ended_at")
    private int endedAt;

    public SessionEntity toSessionEntity() {
        return new SessionEntity(id, startedAt, endedAt);
    }
}
