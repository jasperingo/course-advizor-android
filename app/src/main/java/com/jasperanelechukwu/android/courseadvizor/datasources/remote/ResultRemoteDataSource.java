package com.jasperanelechukwu.android.courseadvizor.datasources.remote;

import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.ResultWebService;
import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.WebService;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResultDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ResultRemoteDataSource {
    private final WebService webService;

    private final ResultWebService resultWebService;

    @Inject
    public ResultRemoteDataSource(WebService webService) {
        this.webService = webService;
        resultWebService = webService.getResultWebService();
    }

    public Single<ResultDto> create(final CreateResultDto dto, final long authId) {
        return resultWebService.create(dto, String.valueOf(authId)).map((response) -> {
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

    public Single<List<ResultDto>> getAll(final long authId) {
        return resultWebService.getAll(String.valueOf(authId)).map((response) -> {
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
