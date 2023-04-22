package com.jasperanelechukwu.android.courseadvizor.datasources.remote;

import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.StudentWebService;
import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.WebService;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class StudentRemoteDataSource {
    private final WebService webService;

    private final StudentWebService studentWebService;

    @Inject
    public StudentRemoteDataSource(WebService webService) {
        this.webService = webService;
        studentWebService = webService.getStudentWebService();
    }

    public Single<List<Student>> getAll(final long authId) {
        return studentWebService.getAll(String.valueOf(authId)).map((response) -> {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else {
                throw new RemoteDataSourceException(
                    webService.convertErrorBody(response, RemoteDataSourceException.RemoteDataSourceError.class)
                );
            }
        });
    }
}
