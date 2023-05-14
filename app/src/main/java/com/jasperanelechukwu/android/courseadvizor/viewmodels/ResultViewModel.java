package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.ResultUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.StudentResultsUiState;
import com.jasperanelechukwu.android.courseadvizor.repositories.ResultRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class ResultViewModel extends ViewModel {
    private final AppStore appStore;

    private final ResultRepository resultRepository;

    private final MutableLiveData<ResultUiState> resultUiState = new MutableLiveData<>();

    private final MutableLiveData<StudentResultsUiState> studentResultsUiState = new MutableLiveData<>();

    private Disposable resultDisposable;

    private String noIdErrorMessage;

    @Inject
    public ResultViewModel(ResultRepository resultRepository, AppStore appStore) {
        this.appStore = appStore;
        this.resultRepository = resultRepository;
    }

    public LiveData<ResultUiState> getResultUiState() {
        if (resultUiState.getValue() == null) {
            resultUiState.setValue(new ResultUiState());
        }

        return resultUiState;
    }

    public LiveData<StudentResultsUiState> getStudentResultsUiState() {
        if (studentResultsUiState.getValue() == null) {
            studentResultsUiState.setValue(new StudentResultsUiState());
        }

        return studentResultsUiState;
    }

    public void setNoIdErrorMessage(String errorMessage) {
        noIdErrorMessage = errorMessage;
    }

    public void fetchResult(long id) {
        if (id == 0) {
            resultUiState.setValue(new ResultUiState(noIdErrorMessage));
            return;
        }

        resultUiState.setValue(new ResultUiState(true));

        resultDisposable = resultRepository.getOneResult(id, appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                result -> resultUiState.setValue(new ResultUiState(result)),
                throwable -> resultUiState.setValue(new ResultUiState(throwable.getMessage()))
            );
    }

    public void fetchStudentResults(long id) {
        studentResultsUiState.setValue(new StudentResultsUiState(true));

        resultDisposable = resultRepository.getOneResultStudents(id, appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                studentWithResults -> studentResultsUiState.setValue(new StudentResultsUiState(studentWithResults, true)),
                throwable -> studentResultsUiState.setValue(new StudentResultsUiState(throwable.getMessage()))
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (resultDisposable != null && !resultDisposable.isDisposed()) {
            resultDisposable.dispose();
        }
    }
}
