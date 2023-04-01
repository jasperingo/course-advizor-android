package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.remote.SessionRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SessionRepository {
    private final SessionRemoteDataSource sessionRemoteDataSource;

    @Inject
    public SessionRepository(SessionRemoteDataSource sessionRemoteDataSource) {
        this.sessionRemoteDataSource = sessionRemoteDataSource;
    }

    public Single<List<Session>> getAllSessions() {
        return sessionRemoteDataSource.getAll()
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Single.error(new RepositoryException(((RemoteDataSourceException) throwable).getData().getMessage()));
                }

                return Single.error(throwable);
            });
    }
}
