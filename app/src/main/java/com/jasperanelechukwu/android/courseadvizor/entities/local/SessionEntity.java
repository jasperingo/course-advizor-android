package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasperanelechukwu.android.courseadvizor.entities.Session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Session")
public class SessionEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private int startedAt;

    private int endedAt;

    public Session toSession() {
        return new Session(id, startedAt, endedAt);
    }
}
