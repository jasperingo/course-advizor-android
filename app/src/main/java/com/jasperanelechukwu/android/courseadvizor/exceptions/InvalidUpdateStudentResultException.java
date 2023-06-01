package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Getter;

@Getter
public class InvalidUpdateStudentResultException extends InvalidFormException {
    private InputError formError;

    private InputError gradeError;

    public InvalidUpdateStudentResultException(String message, InputErrorList inputErrors) {
        super(message, inputErrors);

        for(InputError inputError: inputErrors.getData()) {
            switch (inputError.getName()) {
                case "request_body":
                    formError = inputError;
                    break;

                case "grade":
                    gradeError = inputError;
                    break;
            }
        }
    }
}
