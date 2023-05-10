package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.jasperanelechukwu.android.courseadvizor.entities.local.SessionEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SessionDao {
    @Insert
    Single<List<Long>> insertAll(List<SessionEntity> sessions);

    @Transaction
    @Query("SELECT * FROM session")
    Flowable<List<SessionEntity>> getAll();
}
