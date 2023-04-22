package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.StudentLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.StudentRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StudentRepository {
    private final StudentLocalDataSource studentLocalDataSource;

    private final StudentRemoteDataSource studentRemoteDataSource;

    @Inject
    public StudentRepository(
        StudentLocalDataSource studentLocalDataSource,
        StudentRemoteDataSource studentRemoteDataSource
    ) {
        this.studentLocalDataSource = studentLocalDataSource;
        this.studentRemoteDataSource = studentRemoteDataSource;
    }

    public Flowable<List<Student>> getAllStudents(final long authId) {
        Single<List<Student>> remoteStudents = studentRemoteDataSource.getAll(authId).subscribeOn(Schedulers.io());

        return studentLocalDataSource.getAll()
            .flatMap(students -> {
                if (!students.isEmpty()) {
                    return Flowable.just(students);
                }

                return remoteStudents
                    .flatMap(studentLocalDataSource::createAll)
                    .flatMapObservable(
                        (Function<List<Long>, ObservableSource<List<Student>>>) longs -> studentLocalDataSource.getAll().toObservable()
                    )
                    .toFlowable(BackpressureStrategy.DROP);
            })
            .subscribeOn(Schedulers.io());
    }
}
