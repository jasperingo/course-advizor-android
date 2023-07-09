package com.jasperanelechukwu.android.courseadvizor.datasources.local;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.ReportDao;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntityAndStudentEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class ReportLocalDataSource {
    private final ReportDao reportDao;

    @Inject
    ReportLocalDataSource(AppDatabase appDatabase) {
        this.reportDao = appDatabase.reportDao();
    }

    public Single<List<Long>> createAll(List<ReportEntity> reportEntities) {
        return reportDao.insertAll(reportEntities);
    }

    public Single<Integer> update(final ReportEntity report) {
        return reportDao.update(report);
    }

    public Single<ReportEntity> getOne(final long id) {
        return reportDao.getOne(id);
    }

    public Flowable<List<ReportEntityAndStudentEntity>> getAll() {
        return reportDao.getAll();
    }
}
