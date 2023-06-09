package com.jasperanelechukwu.android.courseadvizor.datasources.remote;

import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.CourseAdviserWebService;
import com.jasperanelechukwu.android.courseadvizor.datasources.webservices.WebService;
import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.AuthCourseAdviserDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateCourseAdviserDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class CourseAdviserRemoteDataSource {
    private final WebService webService;

    private final CourseAdviserWebService courseAdviserWebService;

    @Inject
    public CourseAdviserRemoteDataSource(WebService webService) {
        this.webService = webService;
        courseAdviserWebService = webService.getCourseAdviserWebService();
    }

    public Single<CourseAdviser> create(final CreateCourseAdviserDto dto) {
        return courseAdviserWebService.create(dto).map((response) -> {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else if (response.code() == 400) {
                throw new InvalidFormException(response.message(), webService.convertErrorBody(response, InvalidFormException.InputErrorList.class));
            } else {
                throw new RemoteDataSourceException(
                    webService.convertErrorBody(response, RemoteDataSourceException.RemoteDataSourceError.class)
                );
            }
        });
    }

    public Single<CourseAdviser> auth(final AuthCourseAdviserDto dto) {
        return courseAdviserWebService.auth(dto).map((response) -> {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else if (response.code() == 400) {
                throw new InvalidFormException(response.message(), webService.convertErrorBody(response, InvalidFormException.InputErrorList.class));
            } else {
                throw new RemoteDataSourceException(
                    webService.convertErrorBody(response, RemoteDataSourceException.RemoteDataSourceError.class)
                );
            }
        });
    }
}
