package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.AppointmentLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.AppointmentRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntityAndStudentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.AppointmentDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.UpdateAppointmentDto;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.UpdateAppointmentFormUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidUpdateAppointmentException;
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

    public Single<List<Appointment>> getNewAppointments(final long authId) {
        return appointmentRemoteDataSource.getAll(authId)
            .flatMap(appointmentDtoList -> {
                if (appointmentDtoList.isEmpty()) {
                    return Single.just(new ArrayList<Appointment>());
                }

                return appointmentLocalDataSource.getAll()
                    .firstOrError()
                    .map(entities -> {
                        final List<Appointment> appointments = new ArrayList<>();

                        for (int i = 0; i < appointmentDtoList.size(); i++) {
                            boolean found = false;

                            AppointmentDto dto = appointmentDtoList.get(i);

                            for (int j = 0; j < entities.size(); j++) {
                                AppointmentEntityAndStudentEntity entity = entities.get(j);

                                if (entity.getAppointment().getId() == dto.getId()) {
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                appointments.add(dto.toAppointmentEntity().toAppointment(dto.getStudent().toStudentEntity().toStudent()));
                            }
                        }

                        return appointments;
                    });
            })
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                return Single.error(throwable);
            })
            .subscribeOn(Schedulers.io());
    }

    public Single<Appointment> update(final long appointmentId, final UpdateAppointmentFormUiState uiState, final long authId) {
        final InvalidFormException.InputErrorList inputErrors = new InvalidFormException.InputErrorList();

        if (uiState.getStartedAt() == null) {
            inputErrors.addInputRequired("started_at", uiState.getStartedAt());
        }

        if (inputErrors.hasError()) {
            return Single.error(new InvalidUpdateAppointmentException("Client validation", inputErrors));
        }

        final UpdateAppointmentDto dto = new UpdateAppointmentDto(uiState.getStatus(), uiState.getStartedAt());

        return appointmentRemoteDataSource.update(appointmentId, dto, authId)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                if (throwable instanceof InvalidFormException) {
                    final InvalidFormException formException = (InvalidFormException) throwable;
                    return Single.error(new InvalidUpdateAppointmentException(formException.getMessage(), formException.getInputErrors()));
                }

                return Single.error(throwable);
            })
            .flatMap(appointmentDto -> appointmentLocalDataSource.getOne(appointmentId).flatMap(appointment -> {
                appointment.setStatus(appointmentDto.getStatus());
                appointment.setStartedAt(appointmentDto.getStartedAt());

                return appointmentLocalDataSource.update(appointment)
                    .ignoreElement()
                    .andThen(Single.just(appointment.toAppointment(null)));
            }));
    }
}
