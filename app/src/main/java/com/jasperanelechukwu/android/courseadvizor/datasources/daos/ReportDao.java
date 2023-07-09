package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntityAndStudentEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ReportDao {
    String QUERY_STRING = "SELECT report.reply AS report_reply, report.recordUrl AS report_recordUrl, report.id AS report_id, " +
            "report.createdAt AS report_createdAt, student.id AS student_id, student.firstName AS student_firstName, student.lastName AS student_lastName, " +
            "student.matriculationNumber AS student_matriculationNumber, student.phoneNumber AS student_phoneNumber " +
            "FROM report, student ON report.studentId = student.id";

    @Insert
    Single<List<Long>> insertAll(List<ReportEntity> reports);

    @Update
    Single<Integer> update(ReportEntity report);

    @Query("SELECT * FROM report WHERE report.id = :id")
    Single<ReportEntity> getOne(long id);

    @Transaction
    @Query(QUERY_STRING)
    Flowable<List<ReportEntityAndStudentEntity>> getAll();
}
