package com.jasperanelechukwu.android.courseadvizor.repositories;

import com.jasperanelechukwu.android.courseadvizor.datasources.local.SessionLocalDataSource;
import com.jasperanelechukwu.android.courseadvizor.datasources.remote.SessionRemoteDataSource;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;
import com.jasperanelechukwu.android.courseadvizor.entities.local.SessionEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.remote.SessionDto;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RemoteDataSourceException;
import com.jasperanelechukwu.android.courseadvizor.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SessionRepository {
    private final SessionLocalDataSource sessionLocalDataSource;

    private final SessionRemoteDataSource sessionRemoteDataSource;

    @Inject
    public SessionRepository(
        SessionLocalDataSource sessionLocalDataSource,
        SessionRemoteDataSource sessionRemoteDataSource
    ) {
        this.sessionLocalDataSource = sessionLocalDataSource;
        this.sessionRemoteDataSource = sessionRemoteDataSource;
    }

    private List<Session> convertSessionEntityToSession(List<SessionEntity> sessionEntities) {
        final List<Session> sessions = new ArrayList<>();

        for (SessionEntity entity: sessionEntities) {
            sessions.add(entity.toSession());
        }

        return sessions;
    }

    public Flowable<List<Session>> getAllSessions() {
        final Single<List<SessionDto>> remoteSessions = sessionRemoteDataSource.getAll().subscribeOn(Schedulers.io());

        return sessionLocalDataSource.getAll()
            .flatMap(sessions -> {
                if (!sessions.isEmpty()) {
                    return Flowable.just(convertSessionEntityToSession(sessions));
                }

                return remoteSessions
                    .flatMap(sessionDtoList -> {
                        final List<SessionEntity> sessionEntities = new ArrayList<>();

                        for (SessionDto dto: sessionDtoList) {
                            sessionEntities.add(dto.toSessionEntity());
                        }

                        return sessionLocalDataSource.createAll(sessionEntities);
                    })
                    .ignoreElement()
                    .andThen(sessionLocalDataSource.getAll())
                    .map(this::convertSessionEntityToSession);
            })
            .onErrorResumeNext(throwable -> {
                if (throwable instanceof RemoteDataSourceException) {
                    return Flowable.error(new RepositoryException(((RemoteDataSourceException) throwable).getData().getMessage()));
                }

                return Flowable.error(throwable);
            })
            .subscribeOn(Schedulers.io());
    }
}
