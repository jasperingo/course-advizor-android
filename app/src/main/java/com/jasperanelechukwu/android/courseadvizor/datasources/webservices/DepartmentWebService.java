package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.remote.DepartmentDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResponseDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface DepartmentWebService {
    @GET("department")
    Single<Response<ResponseDto<List<DepartmentDto>>>> getAll();
}
