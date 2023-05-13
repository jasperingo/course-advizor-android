package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.StudentLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.StudentRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;
import com.jasperanelechukwu.android.courseadvizor.entities.local.StudentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.StudentDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
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

    private List<Student> convertStudentEntityToStudent(List<StudentEntity> studentEntities) {
        final List<Student> students = new ArrayList<>();

        for (StudentEntity entity: studentEntities) {
            students.add(entity.toStudent());
        }

        return students;
    }

    public Flowable<List<Student>> getAllStudents(final long authId) {
        Single<List<StudentDto>> remoteStudents = studentRemoteDataSource.getAll(authId).subscribeOn(Schedulers.io());

        return studentLocalDataSource.getAll()
            .flatMap(students -> {
                if (!students.isEmpty()) {
                    return Flowable.just(convertStudentEntityToStudent(students));
                }

                return remoteStudents
                    .flatMap(studentDtoList -> {
                        final List<StudentEntity> studentEntities = new ArrayList<>();

                        for (StudentDto dto: studentDtoList) {
                            studentEntities.add(dto.toStudentEntity());
                        }

                        return studentLocalDataSource.createAll(studentEntities);
                    })
                    .ignoreElement()
                    .andThen(studentLocalDataSource.getAll())
                    .map(this::convertStudentEntityToStudent);
            })
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Flowable.error(new RepositoryException(((RemoteDataSourceException) throwable).getData().getMessage()));
                }

                return Flowable.error(throwable);
            })
            .subscribeOn(Schedulers.io());
    }
}
