package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.remote.ReportDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResponseDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.UpdateReportDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReportWebService {
    @GET("report")
    Single<Response<ResponseDto<List<ReportDto>>>> getAll(@Header("Authorization") String authId);

    @PUT("report/{id}")
    Single<Response<ResponseDto<ReportDto>>> update(
        @Path("id") long id,
        @Body UpdateReportDto updateReportDto,
        @Header("Authorization") String authId
    );
}
