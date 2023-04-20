package com.jasperanelechukwu.android.courseadvizor.datasources.local;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.StudentDao;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class StudentLocalDataSource {
    private final StudentDao studentDao;

    @Inject
    StudentLocalDataSource(AppDatabase appDatabase) {
        this.studentDao = appDatabase.studentDao();
    }

    public Flowable<List<Student>> getAll() {
        return studentDao.getAll();
    }
}
