package com.jasperanelechukwu.android.courseadvizor.datasources.remote;

import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.DepartmentWebService;
import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.WebService;
import com.jasperanelechukwu.android.courseadvizor.entities.Department;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class DepartmentRemoteDataSource {
    private final WebService webService;

    private final DepartmentWebService departmentWebService;

    @Inject
    public DepartmentRemoteDataSource(WebService webService) {
        this.webService = webService;
        departmentWebService = webService.getDepartmentWebService();
    }

    public Single<List<Department>> getAll() {
        return departmentWebService.getAll().map((response) -> {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else {
                throw new RemoteDataSourceException(
                    response.message(),
                    webService.convertErrorBody(response, RemoteDataSourceException.RemoteDataSourceError.class)
                );
            }
        });
    }
}
