package com.jasperanelechukwu.android.courseadvizor.exceptions;

public class RepositoryException extends Exception {
    public RepositoryException(String message) {
        super(message);
    }

    public static RepositoryException from(RemoteDataSourceException throwable) {
        return new RepositoryException(throwable.getData().getMessage());
    }
}
