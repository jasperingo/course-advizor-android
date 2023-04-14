package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.SignInFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.SignInUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidSignInFormException;
import com.jasperanelechukwu.android.courseadvizor.repositories.CourseAdviserRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class SignInViewModel extends ViewModel {
    private final SignInFormUiState signInFormUiState = new SignInFormUiState();

    private final MutableLiveData<SignInUiState> signInUiState = new MutableLiveData<>();

    private final MutableLiveData<Boolean> navigateBack = new MutableLiveData<>();

    private final MutableLiveData<CourseAdviser> courseAdviser = new MutableLiveData<>();

    private final CourseAdviserRepository courseAdviserRepository;

    private Disposable authCourseAdviserDisposable;

    @Inject
    public SignInViewModel(CourseAdviserRepository courseAdviserRepository) {
        this.courseAdviserRepository = courseAdviserRepository;
    }

    public LiveData<Boolean> getNavigateBack() {
        if (navigateBack.getValue() == null) {
            navigateBack.setValue(false);
        }

        return navigateBack;
    }

    public LiveData<SignInUiState> getSignInUiState() {
        if (signInUiState.getValue() == null) {
            signInUiState.setValue(new SignInUiState());
        }

        return signInUiState;
    }

    public LiveData<CourseAdviser> getCourseAdviser() {
        return courseAdviser;
    }

    public void setPhoneNumber(CharSequence value) {
        signInFormUiState.setPhoneNumber(value.toString());
    }

    public void setPin(CharSequence value) {
        signInFormUiState.setPin(value.toString());
    }

    public void signIn() {
        signInUiState.setValue(new SignInUiState(true));

        authCourseAdviserDisposable = courseAdviserRepository.auth(signInFormUiState)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                courseAdviser -> {
                    signInUiState.setValue(new SignInUiState());
                    SignInViewModel.this.courseAdviser.setValue(courseAdviser);
                },
                throwable -> {
                    if (throwable instanceof InvalidSignInFormException) {
                        InvalidSignInFormException exception = (InvalidSignInFormException) throwable;

                        signInUiState.setValue(new SignInUiState(
                            exception.getFormError(),
                            exception.getPinError(),
                            exception.getPhoneNumberError()
                        ));
                    } else {
                        signInUiState.setValue(new SignInUiState(throwable.getMessage()));
                    }
                }
            );
    }

    public void doNavigateBack() {
        navigateBack.setValue(true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (authCourseAdviserDisposable != null && !authCourseAdviserDisposable.isDisposed()){
            authCourseAdviserDisposable.dispose();
        }
    }
}
