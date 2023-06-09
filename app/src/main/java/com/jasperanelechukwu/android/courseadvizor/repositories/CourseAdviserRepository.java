package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.remote.CourseAdviserRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.AuthCourseAdviserDto;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.SignInFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.SignUpFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateCourseAdviserDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidSignInFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidSignUpFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CourseAdviserRepository {
    private final CourseAdviserRemoteDataSource courseAdviserRemoteDataSource;

    @Inject
    public CourseAdviserRepository (CourseAdviserRemoteDataSource courseAdviserRemoteDataSource) {
        this.courseAdviserRemoteDataSource = courseAdviserRemoteDataSource;
    }

    public Single<CourseAdviser> create(final SignUpFormUiState signUpFormUiState) {
        final InvalidFormException.InputErrorList inputErrors = new InvalidFormException.InputErrorList();

        if (signUpFormUiState.getFirstName() == null || signUpFormUiState.getFirstName().trim().isEmpty()) {
            inputErrors.addInputRequired("first_name", signUpFormUiState.getFirstName());
        }

        if (signUpFormUiState.getLastName() == null || signUpFormUiState.getLastName().trim().isEmpty()) {
            inputErrors.addInputRequired("last_name", signUpFormUiState.getLastName());
        }

        if (signUpFormUiState.getPhoneNumber() == null || signUpFormUiState.getPhoneNumber().trim().isEmpty()) {
            inputErrors.addInputRequired("phone_number", signUpFormUiState.getPhoneNumber());
        } else if (!signUpFormUiState.getPhoneNumber().matches("^0\\d{10}$")) {
            inputErrors.addInputInvalid("phone_number", signUpFormUiState.getPhoneNumber());
        }

        if (signUpFormUiState.getPin() == null || signUpFormUiState.getPin().trim().isEmpty()) {
            inputErrors.addInputRequired("pin", signUpFormUiState.getPin());
        } else if (!signUpFormUiState.getPin().matches("^\\d{4}$")) {
            inputErrors.addInputInvalid("pin", signUpFormUiState.getPin());
        }

        if (signUpFormUiState.getSession() == null || signUpFormUiState.getSession().getId() == 0) {
            inputErrors.addInputRequired("session_id", signUpFormUiState.getSession().getId());
        }

        if (signUpFormUiState.getDepartment() == null || signUpFormUiState.getDepartment().getId() == 0) {
            inputErrors.addInputRequired("department_id", signUpFormUiState.getDepartment().getId());
        }

        if (inputErrors.hasError()) {
            return Single.error(new InvalidSignUpFormException("Client validation", inputErrors));
        }

        final CreateCourseAdviserDto dto = new CreateCourseAdviserDto(
            signUpFormUiState.getPin(),
            signUpFormUiState.getFirstName(),
            signUpFormUiState.getLastName(),
            signUpFormUiState.getPhoneNumber(),
            signUpFormUiState.getDepartment().getId(),
            signUpFormUiState.getSession().getId()
        );

        return courseAdviserRemoteDataSource.create(dto)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                if (throwable instanceof InvalidFormException) {
                    final InvalidFormException formException = (InvalidFormException) throwable;
                    return Single.error(new InvalidSignUpFormException(formException.getMessage(), formException.getInputErrors()));
                }

                return Single.error(throwable);
            });
    }

    public Single<CourseAdviser> auth(final SignInFormUiState signInFormUiState) {
        final InvalidFormException.InputErrorList inputErrors = new InvalidFormException.InputErrorList();

        if (signInFormUiState.getPhoneNumber() == null || signInFormUiState.getPhoneNumber().trim().isEmpty()) {
            inputErrors.addInputRequired("phone_number", signInFormUiState.getPhoneNumber());
        } else if (!signInFormUiState.getPhoneNumber().matches("^0\\d{10}$")) {
            inputErrors.addInputInvalid("phone_number", signInFormUiState.getPhoneNumber());
        }

        if (signInFormUiState.getPin() == null || signInFormUiState.getPin().trim().isEmpty()) {
            inputErrors.addInputRequired("pin", signInFormUiState.getPin());
        } else if (!signInFormUiState.getPin().matches("^\\d{4}$")) {
            inputErrors.addInputInvalid("pin", signInFormUiState.getPin());
        }

        if (inputErrors.hasError()) {
            return Single.error(new InvalidSignInFormException("Client validation", inputErrors));
        }

        final AuthCourseAdviserDto dto = new AuthCourseAdviserDto(
            signInFormUiState.getPin(),
            signInFormUiState.getPhoneNumber()
        );

        return courseAdviserRemoteDataSource.auth(dto)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                if (throwable instanceof InvalidFormException) {
                    final InvalidFormException formException = (InvalidFormException) throwable;
                    return Single.error(new InvalidSignInFormException(formException.getMessage(), formException.getInputErrors()));
                }

                return Single.error(throwable);
            });
    }
}

