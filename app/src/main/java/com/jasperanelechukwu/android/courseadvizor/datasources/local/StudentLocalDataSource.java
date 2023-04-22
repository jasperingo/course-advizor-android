package com.jasperanelechukwu.android.courseadvizor.datasources.local;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.StudentDao;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class StudentLocalDataSource {
    private final StudentDao studentDao;

    @Inject
    StudentLocalDataSource(AppDatabase appDatabase) {
        this.studentDao = appDatabase.studentDao();
    }

    public Single<List<Long>> createAll(List<Student> students) {
        return studentDao.insertAll(students);
    }

    public Flowable<List<Student>> getAll() {
        return studentDao.getAll();
    }
}
