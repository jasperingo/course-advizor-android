package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.ReportLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.ReportRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Report;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntityAndStudentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ReportDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReportRepository {
    private final ReportRemoteDataSource reportRemoteDataSource;

    private final ReportLocalDataSource reportLocalDataSource;

    @Inject
    public ReportRepository(ReportRemoteDataSource reportRemoteDataSource, ReportLocalDataSource reportLocalDataSource) {
        this.reportRemoteDataSource = reportRemoteDataSource;
        this.reportLocalDataSource = reportLocalDataSource;
    }

    private List<Report> convertReportEntityAndStudentEntityToReport(List<ReportEntityAndStudentEntity> entities) {
        final List<Report> reports = new ArrayList<>();

        for (ReportEntityAndStudentEntity entity: entities) {
            reports.add(entity.toReport());
        }

        return reports;
    }

    public Flowable<List<Report>> getAllReports(final long authId) {
        final Single<List<ReportDto>> remoteReports = reportRemoteDataSource.getAll(authId).subscribeOn(Schedulers.io());

        return reportLocalDataSource.getAll()
            .flatMap(reports -> {
                if (!reports.isEmpty()) {
                    return Flowable.just(convertReportEntityAndStudentEntityToReport(reports));
                }

                return remoteReports
                    .flatMap(reportDtoList -> {
                        final List<ReportEntity> reportEntities = new ArrayList<>();

                        for (ReportDto dto: reportDtoList) {
                            reportEntities.add(dto.toReportEntity());
                        }

                        return reportLocalDataSource.createAll(reportEntities);
                    })
                    .ignoreElement()
                    .andThen(reportLocalDataSource.getAll())
                    .map(this::convertReportEntityAndStudentEntityToReport);
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
