package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.StudentsUiState;
import com.jasperanelechukwu.android.courseadvizor.repositories.StudentRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class StudentsViewModel extends ViewModel {
    private final MutableLiveData<StudentsUiState> studentsUiState = new MutableLiveData<>();

    private final StudentRepository studentRepository;

    private final AppStore appStore;

    private Disposable studentsDisposable;

    @Inject
    public StudentsViewModel(StudentRepository studentRepository, AppStore appStore) {
        this.appStore = appStore;
        this.studentRepository = studentRepository;
    }

    public LiveData<StudentsUiState> getStudentsUiState() {
        if (studentsUiState.getValue() == null) {
            studentsUiState.setValue(new StudentsUiState());
        }

        return studentsUiState;
    }

    public void fetchStudents() {
        studentsUiState.setValue(new StudentsUiState(true));

        studentsDisposable = studentRepository.getAllStudents(appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                students -> studentsUiState.setValue(new StudentsUiState(students, true)),
                throwable -> studentsUiState.setValue(new StudentsUiState(throwable.getMessage()))
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (studentsDisposable != null && !studentsDisposable.isDisposed()) {
            studentsDisposable.dispose();
        }
    }
}
