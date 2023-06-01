package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.remote.StudentResultRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.StudentResult;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateStudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.StudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.UpdateStudentResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.CreateStudentResultFormUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidCreateStudentResultException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidUpdateStudentResultException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StudentResultRepository {
    public static final String[] GRADES = {"A", "B", "C", "D", "E", "F"};

    private final StudentResultRemoteDataSource studentResultRemoteDataSource;

    @Inject
    public StudentResultRepository(StudentResultRemoteDataSource studentResultRemoteDataSource) {
        this.studentResultRemoteDataSource = studentResultRemoteDataSource;
    }

    private boolean gradeIsNotValid(final String grade) {
        boolean gradeFound = false;

        for (String i: GRADES) {
            if (Objects.equals(i, grade)) {
                gradeFound = true;
                break;
            }
        }

        return !gradeFound;
    }

    public Single<StudentResult> create(final CreateStudentResultFormUiState uiState, final long authId) {
        final InvalidFormException.InputErrorList inputErrors = new InvalidFormException.InputErrorList();

        if (gradeIsNotValid(uiState.getGrade())) {
            inputErrors.addInputRequired("grade", uiState.getGrade());
        }

        if (uiState.getStudentId() < 1) {
            inputErrors.addInputRequired("student_id", uiState.getStudentId());
        }

        if (uiState.getResultId() < 1) {
            inputErrors.addInputRequired("result_id", uiState.getResultId());
        }

        if (inputErrors.hasError()) {
            return Single.error(new InvalidCreateStudentResultException("Client validation", inputErrors));
        }

        final CreateStudentResultDto resultDto = new CreateStudentResultDto(
            uiState.getStudentId(),
            uiState.getResultId(),
            uiState.getGrade()
        );

        return studentResultRemoteDataSource.create(resultDto, authId)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                if (throwable instanceof InvalidFormException) {
                    final InvalidFormException formException = (InvalidFormException) throwable;
                    return Single.error(new InvalidCreateStudentResultException(formException.getMessage(), formException.getInputErrors()));
                }

                return Single.error(throwable);
            })
            .map(StudentResultDto::toStudentResult);
    }

    public Single<StudentResult> update(final long studentResultId, final String grade, final long authId) {
        final InvalidFormException.InputErrorList inputErrors = new InvalidFormException.InputErrorList();

        if (gradeIsNotValid(grade)) {
            inputErrors.addInputRequired("grade", grade);
        }

        if (inputErrors.hasError()) {
            return Single.error(new InvalidUpdateStudentResultException("Client validation", inputErrors));
        }

        final UpdateStudentResultDto resultDto = new UpdateStudentResultDto(grade);

        return studentResultRemoteDataSource.update(studentResultId, resultDto, authId)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                if (throwable instanceof InvalidFormException) {
                    final InvalidFormException formException = (InvalidFormException) throwable;
                    return Single.error(new InvalidUpdateStudentResultException(formException.getMessage(), formException.getInputErrors()));
                }

                return Single.error(throwable);
            })
            .map(StudentResultDto::toStudentResult);
    }
}
