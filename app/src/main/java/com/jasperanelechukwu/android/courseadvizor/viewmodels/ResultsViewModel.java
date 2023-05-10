package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.ResultsUiState;
import com.jasperanelechukwu.android.courseadvizor.repositories.ResultRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class ResultsViewModel extends ViewModel {
    private final MutableLiveData<ResultsUiState> resultsUiState = new MutableLiveData<>();

    private final ResultRepository resultRepository;

    private final AppStore appStore;

    private Disposable resultsDisposable;

    @Inject
    public ResultsViewModel(ResultRepository resultRepository, AppStore appStore) {
        this.appStore = appStore;
        this.resultRepository = resultRepository;
    }

    public LiveData<ResultsUiState> getStudentsUiState() {
        if (resultsUiState.getValue() == null) {
            resultsUiState.setValue(new ResultsUiState());
        }

        return resultsUiState;
    }

    public void fetchResults() {
        resultsUiState.setValue(new ResultsUiState(true));

        resultsDisposable = resultRepository.getAllResults(appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                results -> resultsUiState.setValue(new ResultsUiState(results, true)),
                throwable -> resultsUiState.setValue(new ResultsUiState(throwable.getMessage()))
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (resultsDisposable != null && !resultsDisposable.isDisposed()) {
            resultsDisposable.dispose();
        }
    }
}
