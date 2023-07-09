package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasperanelechukwu.android.courseadvizor.entities.Report;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Report")
public class ReportEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long studentId;

    private String recordUrl;

    private String reply;

    private LocalDateTime createdAt;

    public Report toReport(final Student student) {
        return new Report(id, recordUrl, reply, createdAt, student);
    }
}
