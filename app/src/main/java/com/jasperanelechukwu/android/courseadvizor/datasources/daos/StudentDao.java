package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface StudentDao {
    @Insert
    void insertAll(Student... students);

    @Update
    void delete(Student... students);

    @Query("SELECT * FROM student")
    Flowable<List<Student>> getAll();
}
