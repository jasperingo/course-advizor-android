package com.jasperanelechukwu.android.courseadvizor.services;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;
import com.jasperanelechukwu.android.courseadvizor.repositories.AppointmentRepository;
import com.jasperanelechukwu.android.courseadvizor.ui.notification.AppointmentNotification;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Single;

@HiltWorker
public class CheckNewAppointmentWorker extends RxWorker {
    private final AppStore appStore;

    private final AppointmentRepository appointmentRepository;

    private final AppointmentNotification appointmentNotification;

    @AssistedInject
    public CheckNewAppointmentWorker(
        @Assisted @NonNull Context context,
        @Assisted @NonNull WorkerParameters workerParams,
        AppStore appStore,
        AppointmentRepository appointmentRepository,
        AppointmentNotification appointmentNotification
    ) {
        super(context, workerParams);
        this.appStore = appStore;
        this.appointmentRepository = appointmentRepository;
        this.appointmentNotification = appointmentNotification;
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        return appointmentRepository.getNewAppointments(appStore.getCourseAdviser().getId())
            .map(appointments -> {
                for (Appointment appointment: appointments) {
                    appointmentNotification.send(appointment);
                }

                return Result.success();
            });
    }
}
