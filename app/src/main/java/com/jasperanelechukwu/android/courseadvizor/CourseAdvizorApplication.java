package com.jasperanelechukwu.android.courseadvizor;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

@HiltAndroidApp
public class CourseAdvizorApplication extends Application implements Configuration.Provider {
    @Inject
    HiltWorkerFactory workerFactory;

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

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build();
    }
}
