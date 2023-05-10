package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.ResultLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.ResultRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntityAndSessionEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.ResultDto;

import java.util.ArrayList;
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
            .subscribeOn(Schedulers.io());
    }
}
