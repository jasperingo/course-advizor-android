package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.Student;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResponseDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface StudentWebService {
    @GET("student")
    Single<Response<ResponseDto<List<Student>>>> getAll(@Header("Authorization") String authId);
}
