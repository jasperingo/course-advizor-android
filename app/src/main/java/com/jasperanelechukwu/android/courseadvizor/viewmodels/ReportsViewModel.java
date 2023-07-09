package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.ReportsUiState;
import com.jasperanelechukwu.android.courseadvizor.repositories.ReportRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class ReportsViewModel extends ViewModel {
    private final MutableLiveData<ReportsUiState> reportsUiState = new MutableLiveData<>();

    private final ReportRepository reportRepository;

    private final AppStore appStore;

    private Disposable reportsDisposable;

    @Inject
    public ReportsViewModel(ReportRepository reportRepository, AppStore appStore) {
        this.appStore = appStore;
        this.reportRepository = reportRepository;
    }

    public LiveData<ReportsUiState> getReportsUiState() {
        if (reportsUiState.getValue() == null) {
            reportsUiState.setValue(new ReportsUiState());
        }

        return reportsUiState;
    }

    public void fetchReports() {
        reportsUiState.setValue(new ReportsUiState(true));

        reportsDisposable = reportRepository.getAllReports(appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                reports -> reportsUiState.setValue(new ReportsUiState(reports, true)),
                throwable -> reportsUiState.setValue(new ReportsUiState(throwable.getMessage()))
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
