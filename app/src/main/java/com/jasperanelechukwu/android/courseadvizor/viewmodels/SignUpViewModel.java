package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.entities.Department;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.DepartmentsAndSessionsUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.SignUpFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.SignUpUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidSignUpFormException;
import com.jasperanelechukwu.android.courseadvizor.repositories.CourseAdviserRepository;
import com.jasperanelechukwu.android.courseadvizor.repositories.DepartmentRepository;
import com.jasperanelechukwu.android.courseadvizor.repositories.SessionRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class SignUpViewModel extends ViewModel {
    private final SignUpFormUiState signUpFormUiState = new SignUpFormUiState();

    private final MutableLiveData<CourseAdviser> courseAdviser = new MutableLiveData<>();

    private final MutableLiveData<SignUpUiState> signUpUiState = new MutableLiveData<>();

    private final MutableLiveData<DepartmentsAndSessionsUiState> departmentsAndSessionsUiState = new MutableLiveData<>();

    private final DepartmentRepository departmentRepository;

    private final SessionRepository sessionRepository;

    private final CourseAdviserRepository courseAdviserRepository;

    private Disposable departmentsAndSessionsDisposable;

    private Disposable createCourseAdviserDisposable;

    @Inject
    public SignUpViewModel(
        DepartmentRepository departmentRepository,
        SessionRepository sessionRepository,
        CourseAdviserRepository courseAdviserRepository
    ) {
        this.sessionRepository = sessionRepository;
        this.departmentRepository = departmentRepository;
        this.courseAdviserRepository = courseAdviserRepository;
    }

    public LiveData<CourseAdviser> getCourseAdviser() {
        return courseAdviser;
    }

    public LiveData<SignUpUiState> getSignUpUiState() {
        if (signUpUiState.getValue() == null) {
            signUpUiState.setValue(new SignUpUiState());
        }

        return signUpUiState;
    }

    public LiveData<DepartmentsAndSessionsUiState> getDepartmentsAndSessionsUiState() {
        if (departmentsAndSessionsUiState.getValue() == null) {
            departmentsAndSessionsUiState.setValue(new DepartmentsAndSessionsUiState());
        }

        return departmentsAndSessionsUiState;
    }

    public void setFirstName(CharSequence value) {
        signUpFormUiState.setFirstName(value.toString());
    }

    public void setLastName(CharSequence value) {
        signUpFormUiState.setLastName(value.toString());
    }

    public void setPhoneNumber(CharSequence value) {
        signUpFormUiState.setPhoneNumber(value.toString());
    }

    public void setPin(CharSequence value) {
        signUpFormUiState.setPin(value.toString());
    }

    public void setDepartment(Object department) {
        signUpFormUiState.setDepartment((Department) department);
    }

    public void setSession(Object session) {
        signUpFormUiState.setSession((Session) session);
    }

    public void signUp() {
        signUpUiState.setValue(new SignUpUiState(true));

        createCourseAdviserDisposable = courseAdviserRepository.create(signUpFormUiState)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                courseAdviser -> {
                    signUpUiState.setValue(new SignUpUiState());
                    SignUpViewModel.this.courseAdviser.setValue(courseAdviser);
                },
                throwable -> {
                    if (throwable instanceof InvalidSignUpFormException) {
                        InvalidSignUpFormException exception = (InvalidSignUpFormException) throwable;

                        signUpUiState.setValue(new SignUpUiState(
                            exception.getFormError(),
                            exception.getPinError(),
                            exception.getFirstNameError(),
                            exception.getLastNameError(),
                            exception.getPhoneNumberError(),
                            exception.getDepartmentError(),
                            exception.getSessionError()
                        ));
                    } else {
                        signUpUiState.setValue(new SignUpUiState(throwable.getMessage()));
                    }
                }
            );
    }

    public void fetchDepartmentsAndSessions() {
        departmentsAndSessionsUiState.setValue(
            new DepartmentsAndSessionsUiState(true)
        );

        departmentsAndSessionsDisposable = Single.zip(
            departmentRepository.getAllDepartments(),
            sessionRepository.getAllSessions().firstElement().toSingle(),
            (departments, sessions) -> {
                sessions.add(0, new Session());
                departments.add(0, new Department());
                return new DepartmentsAndSessionsUiState(departments, sessions, true);
            }
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                departmentsAndSessionsUiState::setValue,
                (error) -> departmentsAndSessionsUiState.setValue(new DepartmentsAndSessionsUiState(error.getMessage()))
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (departmentsAndSessionsDisposable != null && !departmentsAndSessionsDisposable.isDisposed()) {
            departmentsAndSessionsDisposable.dispose();
        }

        if (createCourseAdviserDisposable != null && !createCourseAdviserDisposable.isDisposed()){
            createCourseAdviserDisposable.dispose();
        }
    }
}
