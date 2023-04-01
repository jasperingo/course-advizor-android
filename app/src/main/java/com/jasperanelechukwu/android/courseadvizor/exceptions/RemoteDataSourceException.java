package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Data;

public class RemoteDataSourceException extends Exception {
    private final RemoteDataSourceError data;

    public RemoteDataSourceException(String message, RemoteDataSourceError data) {
        super(message);
        this.data = data;
    }

    public RemoteDataSourceError getData() {
        return data;
    }

    @Data
    public static class RemoteDataSourceError {
        private String status;

        private String message;
    }
}
