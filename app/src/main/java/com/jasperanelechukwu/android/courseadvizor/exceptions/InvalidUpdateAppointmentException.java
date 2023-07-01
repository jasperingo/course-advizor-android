package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Getter;

@Getter
public class InvalidUpdateAppointmentException extends InvalidFormException {
    private InputError formError;

    private InputError statusError;

    private InputError startedAtError;

    public InvalidUpdateAppointmentException(String message, InputErrorList inputErrors) {
        super(message, inputErrors);

        for(InputError inputError: inputErrors.getData()) {
            switch (inputError.getName()) {
                case "request_body":
                    formError = inputError;
                    break;

                case "status":
                    statusError = inputError;
                    break;

                case "started_at":
                    startedAtError = inputError;
                    break;
            }
        }
    }
}
