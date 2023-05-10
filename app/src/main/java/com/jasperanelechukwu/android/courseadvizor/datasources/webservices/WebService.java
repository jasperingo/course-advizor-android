package com.jasperanelechukwu.android.courseadvizor.datasources.webservices;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WebService {
    private final Retrofit retrofit;

    @Inject
    public WebService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public CourseAdviserWebService getCourseAdviserWebService() {
        return retrofit.create(CourseAdviserWebService.class);
    }

    public DepartmentWebService getDepartmentWebService() {
        return retrofit.create(DepartmentWebService.class);
    }

    public SessionWebService getSessionWebService() {
        return retrofit.create(SessionWebService.class);
    }

    public StudentWebService getStudentWebService() {
        return retrofit.create(StudentWebService.class);
    }

    public ResultWebService getResultWebService() {
        return retrofit.create(ResultWebService.class);
    }

    public <T> T convertErrorBody(Response<?> response, Class<T> classObj) throws IOException {
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(classObj, new Annotation[0]);

        return converter.convert(Objects.requireNonNull(Objects.requireNonNull(response).errorBody()));
    }
}

