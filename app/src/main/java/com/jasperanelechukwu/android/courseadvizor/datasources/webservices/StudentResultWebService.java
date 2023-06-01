package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateStudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResponseDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.StudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.UpdateStudentResultDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentResultWebService {
    @POST("student-result")
    Single<Response<ResponseDto<StudentResultDto>>> create(@Body CreateStudentResultDto createStudentResultDto, @Header("Authorization") String authId);

    @PUT("student-result/{id}")
    Single<Response<ResponseDto<StudentResultDto>>> update(@Path("id") long id, @Body UpdateStudentResultDto updateStudentResultDto, @Header("Authorization") String authId);
}
