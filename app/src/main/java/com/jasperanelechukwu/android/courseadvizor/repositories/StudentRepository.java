package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.StudentLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StudentRepository {
    private final StudentLocalDataSource studentLocalDataSource;

    @Inject
    public StudentRepository(StudentLocalDataSource studentLocalDataSource) {
        this.studentLocalDataSource = studentLocalDataSource;
    }

    public Flowable<List<Student>> getAllStudents() {
        return studentLocalDataSource.getAll().subscribeOn(Schedulers.io());
    }
}
