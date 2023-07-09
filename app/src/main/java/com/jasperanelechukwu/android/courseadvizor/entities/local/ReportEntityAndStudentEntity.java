package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Embedded;

import com.jasperanelechukwu.android.courseadvizor.entities.Report;

import lombok.Data;

@Data
public class ReportEntityAndStudentEntity {
    @Embedded(prefix = "report_")
    private ReportEntity report;

    @Embedded(prefix = "student_")
    private StudentEntity student;

    public Report toReport() {
        return report.toReport(student.toStudent());
    }
}
