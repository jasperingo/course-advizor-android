package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Getter;

@Getter
public class InvalidUpdateReportException extends InvalidFormException {
    private InputError formError;

    private InputError replyError;

    public InvalidUpdateReportException(String message, InputErrorList inputErrors) {
        super(message, inputErrors);

        for(InputError inputError: inputErrors.getData()) {
            switch (inputError.getName()) {
                case "request_body":
                    formError = inputError;
                    break;

                case "reply":
                    replyError = inputError;
                    break;
            }
        }
    }
}
