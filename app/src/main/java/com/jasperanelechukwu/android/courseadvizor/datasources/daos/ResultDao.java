package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntityAndSessionEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ResultDao {
    @Insert
    Single<List<Long>> insertAll(List<ResultEntity> results);

    @Transaction
    @Query("SELECT result.semester AS result_semester, result.courseCode AS result_courseCode, result.id AS result_id, result.createdAt AS result_createdAt, " +
            "session.id AS session_id, session.endedAt AS session_endedAt, session.startedAt AS session_startedAt " +
            "FROM result, session ON result.sessionId = session.id")
    Flowable<List<ResultEntityAndSessionEntity>> getAll();
}
