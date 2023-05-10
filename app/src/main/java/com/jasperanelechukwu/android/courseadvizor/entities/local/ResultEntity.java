package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Result")
public class ResultEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long sessionId;

    private String courseCode;

    private Result.Semester semester;

    private String createdAt;

    public Result toResult(final Session session) {
        return new Result(id, courseCode, semester, createdAt, session);
    }
}
