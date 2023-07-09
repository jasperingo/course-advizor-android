package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.UpdateReportFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.UpdateReportUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidUpdateReportException;
import com.jasperanelechukwu.android.courseadvizor.repositories.ReportRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class ReportUpdateViewModel extends ViewModel {
    private final UpdateReportFormUiState updateReportFormUiState = new UpdateReportFormUiState();

    private final MutableLiveData<UpdateReportUiState> reportsUiState = new MutableLiveData<>();

    private final ReportRepository reportRepository;

    private final AppStore appStore;

    private Disposable reportsDisposable;

    @Inject
    public ReportUpdateViewModel(ReportRepository reportRepository, AppStore appStore) {
        this.appStore = appStore;
        this.reportRepository = reportRepository;
    }

    public LiveData<UpdateReportUiState> getReportsUiState() {
        if (reportsUiState.getValue() == null) {
            reportsUiState.setValue(new UpdateReportUiState());
        }

        return reportsUiState;
    }

    public void resetUpdateReportUiState() {
        reportsUiState.setValue(new UpdateReportUiState());
    }

    public void updateReport(final long reportId, final String reply) {
        reportsUiState.setValue(new UpdateReportUiState(true));

        updateReportFormUiState.setReply(reply);

        reportsDisposable = reportRepository.update(reportId, updateReportFormUiState, appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                report -> reportsUiState.setValue(new UpdateReportUiState(report)),
                throwable -> {
                    if (throwable instanceof InvalidUpdateReportException) {
                        final InvalidUpdateReportException exception = (InvalidUpdateReportException) throwable;

                        reportsUiState.setValue(new UpdateReportUiState(
                            exception.getReplyError() != null
                                ? exception.getReplyError()
                                : exception.getFormError()
                        ));
                    } else {
                        reportsUiState.setValue(new UpdateReportUiState(throwable.getMessage()));
                    }
                }
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (reportsDisposable != null && !reportsDisposable.isDisposed()) {
            reportsDisposable.dispose();
        }
    }
}
