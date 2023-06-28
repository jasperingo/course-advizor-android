package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntityAndStudentEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AppointmentDao {
    String QUERY_STRING = "SELECT appointment.status AS appointment_status, appointment.startedAt AS appointment_startedAt, appointment.id AS appointment_id, " +
            "appointment.createdAt AS appointment_createdAt, student.id AS student_id, student.firstName AS student_firstName, student.lastName AS student_lastName, " +
            "student.matriculationNumber AS student_matriculationNumber, student.phoneNumber AS student_phoneNumber " +
            "FROM appointment, student ON appointment.studentId = student.id";

    @Insert
    Single<List<Long>> insertAll(List<AppointmentEntity> appointments);

    @Transaction
    @Query(QUERY_STRING)
    Flowable<List<AppointmentEntityAndStudentEntity>> getAll();
}
