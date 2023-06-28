package com.jasperanelechukwu.android.courseadvizor.datasources.remote;

import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.AppointmentWebService;
import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.WebService;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.AppointmentDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AppointmentRemoteDataSource {
    private final WebService webService;

    private final AppointmentWebService appointmentWebService;

    @Inject
    public AppointmentRemoteDataSource(WebService webService) {
        this.webService = webService;
        appointmentWebService = webService.getAppointmentWebService();
    }

    public Single<List<AppointmentDto>> getAll(final long authId) {
        return appointmentWebService.getAll(String.valueOf(authId)).map((response) -> {
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
