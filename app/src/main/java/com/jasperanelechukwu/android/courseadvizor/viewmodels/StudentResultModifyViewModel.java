package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.CreateStudentResultFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.ModifyStudentResultUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidCreateStudentResultException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidUpdateStudentResultException;
import com.jasperanelechukwu.android.courseadvizor.repositories.StudentResultRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class StudentResultModifyViewModel extends ViewModel {
    private final AppStore appStore;

    private final StudentResultRepository studentResultRepository;

    private final CreateStudentResultFormUiState createStudentResultFormUiState = new CreateStudentResultFormUiState();

    private final MutableLiveData<ModifyStudentResultUiState> modifyStudentResultUiState = new MutableLiveData<>();

    private Disposable updateStudentResultDisposable;

    private Disposable createStudentResultDisposable;

    @Inject
    public StudentResultModifyViewModel(AppStore appStore, StudentResultRepository studentResultRepository) {
        this.appStore = appStore;
        this.studentResultRepository = studentResultRepository;
    }

    public LiveData<ModifyStudentResultUiState> getModifyStudentResultUiState() {
        if (modifyStudentResultUiState.getValue() == null) {
            modifyStudentResultUiState.setValue(new ModifyStudentResultUiState());
        }

        return modifyStudentResultUiState;
    }

    public void resetModifyStudentResultUiState() {
        modifyStudentResultUiState.setValue(new ModifyStudentResultUiState());
    }

    public void setGrade(CharSequence value) {
        createStudentResultFormUiState.setGrade(value.toString());
    }

    public void setStudentId(long value) {
        createStudentResultFormUiState.setStudentId(value);
    }

    public void setResultId(long value) {
        createStudentResultFormUiState.setResultId(value);
    }

    public void createStudentResult() {
        modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(true));

        createStudentResultDisposable = studentResultRepository.create(createStudentResultFormUiState, appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                studentResult -> modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(studentResult, createStudentResultFormUiState.getStudentId())),
                throwable -> {
                    if (throwable instanceof InvalidCreateStudentResultException) {
                        InvalidCreateStudentResultException exception = (InvalidCreateStudentResultException) throwable;

                        modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(
                            exception.getGradeError() != null
                                ? exception.getGradeError()
                                : exception.getStudentIdError() != null
                                    ? exception.getStudentIdError()
                                    : exception.getResultIdError() != null
                                        ? exception.getResultIdError()
                                        : exception.getFormError()
                        ));
                    } else {
                        modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(throwable.getMessage()));
                    }
                }
            );
    }

    public void updateStudentResult(final long studentResultId) {
        modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(true));

        updateStudentResultDisposable = studentResultRepository.update(
            studentResultId,
            createStudentResultFormUiState.getGrade(),
            appStore.getCourseAdviser().getId()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                studentResult -> modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(studentResult, createStudentResultFormUiState.getStudentId())),
                throwable -> {
                    if (throwable instanceof InvalidUpdateStudentResultException) {
                        InvalidUpdateStudentResultException exception = (InvalidUpdateStudentResultException) throwable;

                        modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(
                            exception.getGradeError() != null
                                ? exception.getGradeError()
                                : exception.getFormError()
                        ));
                    } else {
                        modifyStudentResultUiState.setValue(new ModifyStudentResultUiState(throwable.getMessage()));
                    }
                }
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (createStudentResultDisposable != null && !createStudentResultDisposable.isDisposed()) {
            createStudentResultDisposable.dispose();
        }

        if (updateStudentResultDisposable != null && !updateStudentResultDisposable.isDisposed()){
            updateStudentResultDisposable.dispose();
        }
    }
}
