package com.jasperanelechukwu.android.courseadvizor.datasources.remote;

import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.ReportWebService;
import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.WebService;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ReportDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.UpdateReportDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ReportRemoteDataSource {
    private final WebService webService;

    private final ReportWebService reportWebService;

    @Inject
    public ReportRemoteDataSource(WebService webService) {
        this.webService = webService;
        reportWebService = webService.getReportWebService();
    }

    public Single<List<ReportDto>> getAll(final long authId) {
        return reportWebService.getAll(String.valueOf(authId)).map((response) -> {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else {
                throw new RemoteDataSourceException(
                    webService.convertErrorBody(response, RemoteDataSourceException.RemoteDataSourceError.class)
                );
            }
        });
    }

    public Single<ReportDto> update(final long id, final UpdateReportDto dto, final long authId) {
        return reportWebService.update(id, dto, String.valueOf(authId)).map((response) -> {
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
