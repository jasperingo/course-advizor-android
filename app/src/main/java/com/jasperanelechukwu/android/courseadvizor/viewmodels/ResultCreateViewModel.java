package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.CreateResultFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.CreateResultUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.SessionsUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidCreateResultException;
import com.jasperanelechukwu.android.courseadvizor.repositories.ResultRepository;
import com.jasperanelechukwu.android.courseadvizor.repositories.SessionRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class ResultCreateViewModel extends ViewModel {
    private final AppStore appStore;

    private final SessionRepository sessionRepository;

    private final ResultRepository resultRepository;

    private final CreateResultFormUiState createResultFormUiState = new CreateResultFormUiState();

    private final MutableLiveData<CreateResultUiState> createResultUiState = new MutableLiveData<>();

    private final MutableLiveData<SessionsUiState> sessionsUiState = new MutableLiveData<>();

    private Disposable fetchSessionsDisposable;

    private Disposable createResultDisposable;

    @Inject
    public ResultCreateViewModel(
        AppStore appStore,
        SessionRepository sessionRepository,
        ResultRepository resultRepository
    ) {
        this.appStore = appStore;
        this.sessionRepository = sessionRepository;
        this.resultRepository = resultRepository;
    }

    public LiveData<CreateResultUiState> getCreateResultUiState() {
        if (createResultUiState.getValue() == null) {
            createResultUiState.setValue(new CreateResultUiState());
        }

        return createResultUiState;
    }

    public LiveData<SessionsUiState> getSessionsUiState() {
        if (sessionsUiState.getValue() == null) {
            sessionsUiState.setValue(new SessionsUiState());
        }

        return sessionsUiState;
    }

    public void setCourseCode(CharSequence value) {
        createResultFormUiState.setCourseCode(value.toString());
    }

    public void setSemester(Object semester) {
        createResultFormUiState.setSemester((Result.Semester) semester);
    }

    public void setSession(Object session) {
        createResultFormUiState.setSession((Session) session);
    }

    public void fetchSessions() {
        sessionsUiState.setValue(new SessionsUiState(true));

        fetchSessionsDisposable = sessionRepository.getAllSessions()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                sessions -> {
                    sessions.add(0, new Session());
                    sessionsUiState.setValue(new SessionsUiState(sessions, true));
                },
                error -> sessionsUiState.setValue(new SessionsUiState(error.getMessage()))
            );
    }

    public void createResult() {
        createResultUiState.setValue(new CreateResultUiState(true));

        createResultDisposable = resultRepository.create(createResultFormUiState, appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                result -> createResultUiState.setValue(new CreateResultUiState(result)),
                throwable -> {
                    Log.e("Create result error", throwable.getMessage(), throwable);

                    if (throwable instanceof InvalidCreateResultException) {
                        InvalidCreateResultException exception = (InvalidCreateResultException) throwable;

                        createResultUiState.setValue(new CreateResultUiState(
                            exception.getFormError(),
                            exception.getCourseCodeError(),
                            exception.getSemesterError(),
                            exception.getSessionError()
                        ));
                    } else {
                        createResultUiState.setValue(new CreateResultUiState(throwable.getMessage()));
                    }
                }
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (fetchSessionsDisposable != null && !fetchSessionsDisposable.isDisposed()) {
            fetchSessionsDisposable.dispose();
        }

        if (createResultDisposable != null && !createResultDisposable.isDisposed()){
            createResultDisposable.dispose();
        }
    }
}
