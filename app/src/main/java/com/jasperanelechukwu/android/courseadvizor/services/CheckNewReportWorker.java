package com.jasperanelechukwu.android.courseadvizor.services;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jasperanelechukwu.android.courseadvizor.entities.Report;
import com.jasperanelechukwu.android.courseadvizor.repositories.ReportRepository;
import com.jasperanelechukwu.android.courseadvizor.ui.notification.ReportNotification;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Single;

@HiltWorker
public class CheckNewReportWorker extends RxWorker {
    private final AppStore appStore;

    private final ReportRepository reportRepository;

    private final ReportNotification reportNotification;

    @AssistedInject
    public CheckNewReportWorker(
        @Assisted @NonNull Context context,
        @Assisted @NonNull WorkerParameters workerParams,
        AppStore appStore,
        ReportRepository reportRepository,
        ReportNotification reportNotification
    ) {
        super(context, workerParams);
        this.appStore = appStore;
        this.reportRepository = reportRepository;
        this.reportNotification = reportNotification;
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        return reportRepository.getNewReports(appStore.getCourseAdviser().getId())
            .map(reports -> {
                for (Report report: reports) {
                    reportNotification.send(report);
                }

                return Result.success();
            });
    }
}
