package com.jasperanelechukwu.android.courseadvizor.datasources.local;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppointmentDao;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntityAndStudentEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class AppointmentLocalDataSource {
    private final AppointmentDao appointmentDao;

    @Inject
    AppointmentLocalDataSource(AppDatabase appDatabase) {
        this.appointmentDao = appDatabase.appointmentDao();
    }

    public Single<List<Long>> createAll(List<AppointmentEntity> appointmentEntities) {
        return appointmentDao.insertAll(appointmentEntities);
    }

    public Single<Integer> update(final AppointmentEntity appointment) {
        return appointmentDao.update(appointment);
    }

    public Single<AppointmentEntity> getOne(final long id) {
        return appointmentDao.getOne(id);
    }

    public Flowable<List<AppointmentEntityAndStudentEntity>> getAll() {
        return appointmentDao.getAll();
    }
}
