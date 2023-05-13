package com.jasperanelechukwu.android.courseadvizor.datasources.local;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.SessionDao;
import com.jasperanelechukwu.android.courseadvizor.entities.local.SessionEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class SessionLocalDataSource {
    private final SessionDao sessionDao;

    @Inject
    public SessionLocalDataSource(AppDatabase appDatabase) {
        this.sessionDao = appDatabase.sessionDao();
    }

    public Single<List<Long>> createAll(List<SessionEntity> sessions) {
        return sessionDao.insertAll(sessions);
    }

    public Flowable<List<SessionEntity>> getAll() {
        return sessionDao.getAll();
    }
}
