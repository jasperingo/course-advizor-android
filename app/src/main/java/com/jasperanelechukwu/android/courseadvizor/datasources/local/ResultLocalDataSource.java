package com.jasperanelechukwu.android.courseadvizor.datasources.local;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.ResultDao;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntityAndSessionEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class ResultLocalDataSource {
    private final ResultDao resultDao;

    @Inject
    ResultLocalDataSource(AppDatabase appDatabase) {
        this.resultDao = appDatabase.resultDao();
    }

    public Single<List<Long>> createAll(List<ResultEntity> resultEntities) {
        return resultDao.insertAll(resultEntities);
    }

    public Flowable<List<ResultEntityAndSessionEntity>> getAll() {
        return resultDao.getAll();
    }

    public Single<ResultEntityAndSessionEntity> getOne(long id) {
        return resultDao.getOne(id);
    }
}
