package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.remote.AppointmentDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResponseDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.UpdateAppointmentDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AppointmentWebService {
    @GET("appointment")
    Single<Response<ResponseDto<List<AppointmentDto>>>> getAll(@Header("Authorization") String authId);

    @PUT("appointment/{id}")
    Single<Response<ResponseDto<AppointmentDto>>> update(
        @Path("id") long id,
        @Body UpdateAppointmentDto updateAppointmentDto,
        @Header("Authorization") String authId
    );
}
