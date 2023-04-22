package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface StudentDao {
    @Insert
    Single<List<Long>> insertAll(List<Student> students);

    @Query("SELECT * FROM student")
    Flowable<List<Student>> getAll();
}
