package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.entities.webservice.CreateCourseAdviserDto;
import com.jasperanelechukwu.android.courseadvizor.entities.webservice.WebServiceDto;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CourseAdviserWebService {
    @POST("course-adviser")
    Single<Response<WebServiceDto<CourseAdviser>>> create(@Body CreateCourseAdviserDto createCourseAdviserDto);
}
