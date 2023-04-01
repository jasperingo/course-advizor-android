package com.jasperanelechukwu.android.courseadvizor.utils;

import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Data;

@Data
@Singleton
public class AppStore {
    private CourseAdviser courseAdviser;

    @Inject
    public AppStore() {}

    public String getAuthToken() {
        return "Bearer "+courseAdviser.getId();
    }
}
