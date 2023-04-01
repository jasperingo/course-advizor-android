package com.jasperanelechukwu.android.courseadvizor.entities.webservice;

import lombok.Data;

@Data
public class WebServiceDto<T> {
    private String status;

    private String message;

    private T data;
}
