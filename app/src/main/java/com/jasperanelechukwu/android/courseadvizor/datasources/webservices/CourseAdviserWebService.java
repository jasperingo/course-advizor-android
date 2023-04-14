package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.AuthCourseAdviserDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateCourseAdviserDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResponseDto;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CourseAdviserWebService {
    @POST("course-adviser")
    Single<Response<ResponseDto<CourseAdviser>>> create(@Body CreateCourseAdviserDto createCourseAdviserDto);

    @POST("course-adviser/auth")
    Single<Response<ResponseDto<CourseAdviser>>> auth(@Body AuthCourseAdviserDto authCourseAdviserDto);
}
