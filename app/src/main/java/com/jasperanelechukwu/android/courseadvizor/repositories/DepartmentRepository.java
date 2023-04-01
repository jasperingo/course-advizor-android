package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.remote.DepartmentRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Department;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DepartmentRepository {
    private final DepartmentRemoteDataSource departmentRemoteDataSource;

    @Inject
    public DepartmentRepository(DepartmentRemoteDataSource departmentRemoteDataSource) {
        this.departmentRemoteDataSource = departmentRemoteDataSource;
    }

    public Single<List<Department>> getAllDepartments() {
        return departmentRemoteDataSource.getAll()
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(new RepositoryException(((RemoteDataSourceException) throwable).getData().getMessage()));
                }

                return Single.error(throwable);
            });
    }
}
