package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.AppointmentLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.AppointmentRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntityAndStudentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.AppointmentDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppointmentRepository {
    private final AppointmentRemoteDataSource appointmentRemoteDataSource;

    private final AppointmentLocalDataSource appointmentLocalDataSource;

    @Inject
    public AppointmentRepository(AppointmentRemoteDataSource appointmentRemoteDataSource, AppointmentLocalDataSource appointmentLocalDataSource) {
        this.appointmentRemoteDataSource = appointmentRemoteDataSource;
        this.appointmentLocalDataSource = appointmentLocalDataSource;
    }

    private List<Appointment> convertAppointmentEntityAndStudentEntityToAppointment(List<AppointmentEntityAndStudentEntity> entities) {
        final List<Appointment> appointments = new ArrayList<>();

        for (AppointmentEntityAndStudentEntity entity: entities) {
            appointments.add(entity.toAppointment());
        }

        return appointments;
    }

    public Flowable<List<Appointment>> getAllAppointments(final long authId) {
        final Single<List<AppointmentDto>> remoteAppointments = appointmentRemoteDataSource.getAll(authId).subscribeOn(Schedulers.io());

        return appointmentLocalDataSource.getAll()
            .flatMap(appointments -> {
                if (!appointments.isEmpty()) {
                    return Flowable.just(convertAppointmentEntityAndStudentEntityToAppointment(appointments));
                }

                return remoteAppointments
                    .flatMap(appointmentDtoList -> {
                        final List<AppointmentEntity> appointmentEntities = new ArrayList<>();

                        for (AppointmentDto dto: appointmentDtoList) {
                            appointmentEntities.add(dto.toAppointmentEntity());
                        }

                        return appointmentLocalDataSource.createAll(appointmentEntities);
                    })
                    .ignoreElement()
                    .andThen(appointmentLocalDataSource.getAll())
                    .map(this::convertAppointmentEntityAndStudentEntityToAppointment);
            })
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Flowable.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                return Flowable.error(throwable);
            })
            .subscribeOn(Schedulers.io());
    }
}
