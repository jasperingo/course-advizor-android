package com.jasperanelechukwu.android.courseadvizor;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

@HiltAndroidApp
public class CourseAdvizorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                Log.w("Undeliverable exception", e);
            } else {
                Objects.requireNonNull(Thread.currentThread().getUncaughtExceptionHandler())
                        .uncaughtException(Thread.currentThread(), e);
            }
        });
    }
}
