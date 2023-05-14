package com.jasperanelechukwu.android.courseadvizor.repositories;

import androidx.room.rxjava3.EmptyResultSetException;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.ResultLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.ResultRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.entities.StudentWithResult;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntityAndSessionEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.CreateResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.StudentWithResultDto;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.CreateResultFormUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidCreateResultException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResultRepository {
    private final ResultRemoteDataSource resultRemoteDataSource;

    private final ResultLocalDataSource resultLocalDataSource;

    @Inject
    public ResultRepository(ResultRemoteDataSource resultRemoteDataSource, ResultLocalDataSource resultLocalDataSource) {
        this.resultRemoteDataSource = resultRemoteDataSource;
        this.resultLocalDataSource = resultLocalDataSource;
    }

    private List<Result> convertResultEntityAndSessionEntityToResult(List<ResultEntityAndSessionEntity> resultEntityAndSessionEntities) {
        final List<Result> resultsList = new ArrayList<>();

        for (ResultEntityAndSessionEntity entity: resultEntityAndSessionEntities) {
            resultsList.add(entity.toResult());
        }

        return resultsList;
    }

    public Single<Result> create(final CreateResultFormUiState createResultFormUiState, final long authId) {
        final InvalidFormException.InputErrorList inputErrors = new InvalidFormException.InputErrorList();

        if (createResultFormUiState.getCourseCode() == null || createResultFormUiState.getCourseCode().trim().isEmpty()) {
            inputErrors.addInputRequired("course_code", createResultFormUiState.getCourseCode());
        } else if (!createResultFormUiState.getCourseCode().matches("^[A-Z]{3}\\d{3}$")) {
            inputErrors.addInputInvalid("course_code", createResultFormUiState.getCourseCode());
        }

        if (createResultFormUiState.getSemester() == null) {
            inputErrors.addInputRequired("semester", createResultFormUiState.getSemester());
        }

        if (createResultFormUiState.getSession() == null || createResultFormUiState.getSession().getId() == 0) {
            inputErrors.addInputRequired("session_id", createResultFormUiState.getSession().getId());
        }

        if (inputErrors.hasError()) {
            return Single.error(new InvalidCreateResultException("Client validation", inputErrors));
        }

        final CreateResultDto resultDto = new CreateResultDto(
            createResultFormUiState.getCourseCode(),
            createResultFormUiState.getSemester(),
            createResultFormUiState.getSession().getId()
        );

        return resultRemoteDataSource.create(resultDto, authId)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                if (throwable instanceof InvalidFormException) {
                    final InvalidFormException formException = (InvalidFormException) throwable;
                    return Single.error(new InvalidCreateResultException(formException.getMessage(), formException.getInputErrors()));
                }

                return Single.error(throwable);
            })
            .flatMap(resultDto1 ->
                resultLocalDataSource.createAll(Collections.singletonList(resultDto1.toResultEntity()))
                    .ignoreElement()
                    .andThen(
                        Single.just(
                            resultDto1
                            .toResultEntity()
                            .toResult(resultDto1.getSession().toSessionEntity().toSession())
                        )
                    )
            );
    }

    public Flowable<List<Result>> getAllResults(final long authId) {
        final Single<List<ResultDto>> remoteResults = resultRemoteDataSource.getAll(authId).subscribeOn(Schedulers.io());

        return resultLocalDataSource.getAll()
            .flatMap(results -> {
                if (!results.isEmpty()) {
                    return Flowable.just(convertResultEntityAndSessionEntityToResult(results));
                }

                return remoteResults
                    .flatMap(resultDtoList -> {
                        final List<ResultEntity> resultEntities = new ArrayList<>();

                        for (ResultDto dto: resultDtoList) {
                            resultEntities.add(dto.toResultEntity());
                        }

                        return resultLocalDataSource.createAll(resultEntities);
                    })
                    .ignoreElement()
                    .andThen(resultLocalDataSource.getAll())
                    .map(this::convertResultEntityAndSessionEntityToResult);
            })
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Flowable.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                return Flowable.error(throwable);
            })
            .subscribeOn(Schedulers.io());
    }

    public Single<Result> getOneResult(final long id, final long authId) {
        final Single<ResultDto> remoteResult = resultRemoteDataSource.getOne(id, authId).subscribeOn(Schedulers.io());

        return resultLocalDataSource.getOne(id)
            .map(ResultEntityAndSessionEntity::toResult)
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof EmptyResultSetException) {
                    return remoteResult
                        .flatMap(resultDto -> resultLocalDataSource.createAll(Collections.singletonList(resultDto.toResultEntity())))
                        .ignoreElement()
                        .andThen(resultLocalDataSource.getOne(id))
                        .map(ResultEntityAndSessionEntity::toResult);
                }

                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                return Single.error(throwable);
            })
            .subscribeOn(Schedulers.io());
    }

    public Single<List<StudentWithResult>> getOneResultStudents(final long id, final long authId) {
        return resultRemoteDataSource.getOneStudents(id, authId)
            .map(studentWithResultDtoList -> {
                final List<StudentWithResult> studentWithResults = new ArrayList<>();

                for (StudentWithResultDto dto: studentWithResultDtoList) {
                    studentWithResults.add(dto.toStudentWithResult());
                }

                return studentWithResults;
            })
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(RepositoryException.from((RemoteDataSourceException) throwable));
                }

                return Single.error(throwable);
            })
            .subscribeOn(Schedulers.io());
    }
}
