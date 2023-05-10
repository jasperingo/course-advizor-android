package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Embedded;

import com.jasperanelechukwu.android.courseadvizor.entities.Result;

import lombok.Data;

@Data
public class ResultEntityAndSessionEntity {
    @Embedded(prefix = "result_")
    private ResultEntity result;

    @Embedded(prefix = "session_")
    private SessionEntity session;

    public Result toResult() {
        return result.toResult(session.toSession());
    }
}
