package com.jasperanelechukwu.android.courseadvizor.services;

import android.content.Context;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class PersistentWorkFactory {
    private final WorkManager workManager;

    @Inject
    public PersistentWorkFactory(@ApplicationContext Context context) {
        this.workManager = WorkManager.getInstance(context);
    }

    public void createWorkers() {
        workManager.enqueue(new PeriodicWorkRequest.Builder(CheckNewAppointmentWorker.class, 15, TimeUnit.MINUTES).build());
    }
}
