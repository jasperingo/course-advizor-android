package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private String status;

    private String message;

    private T data;
}
