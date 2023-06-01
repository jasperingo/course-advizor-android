package com.jasperanelechukwu.android.courseadvizor.datasources.remote;

import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.StudentResultWebService;
import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.WebService;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateStudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.StudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.UpdateStudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class StudentResultRemoteDataSource {
    private final WebService webService;

    private final StudentResultWebService studentResultWebService;

    @Inject
    public StudentResultRemoteDataSource(WebService webService) {
        this.webService = webService;
        studentResultWebService = webService.getStudentResultWebService();
    }

    public Single<StudentResultDto> create(final CreateStudentResultDto dto, final long authId) {
        return studentResultWebService.create(dto, String.valueOf(authId)).map((response) -> {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else if (response.code() == 400) {
                throw new InvalidFormException(response.message(), webService.convertErrorBody(response, InvalidFormException.InputErrorList.class));
            } else {
                throw new RemoteDataSourceException(
                    webService.convertErrorBody(response, RemoteDataSourceException.RemoteDataSourceError.class)
                );
            }
        });
    }

    public Single<StudentResultDto> update(final long studentResultId, final UpdateStudentResultDto dto, final long authId) {
        return studentResultWebService.update(studentResultId, dto, String.valueOf(authId)).map((response) -> {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else if (response.code() == 400) {
                throw new InvalidFormException(response.message(), webService.convertErrorBody(response, InvalidFormException.InputErrorList.class));
            } else {
                throw new RemoteDataSourceException(
                    webService.convertErrorBody(response, RemoteDataSourceException.RemoteDataSourceError.class)
                );
            }
        });
    }
}
