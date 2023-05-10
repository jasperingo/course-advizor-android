package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResponseDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResultDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ResultWebService {
    @POST("result")
    Single<Response<ResponseDto<ResultDto>>> create(@Body CreateResultDto createResultDto, @Header("Authorization") String authId);

    @GET("result")
    Single<Response<ResponseDto<List<ResultDto>>>> getAll(@Header("Authorization") String authId);
}
