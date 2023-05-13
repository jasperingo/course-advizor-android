package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Getter;

@Getter
public class InvalidCreateResultException extends InvalidFormException {
    private InputError formError;

    private InputError courseCodeError;

    private InputError semesterError;

    private InputError sessionError;

    public InvalidCreateResultException(String message, InputErrorList inputErrors) {
        super(message, inputErrors);

        for(InputError inputError: inputErrors.getData()) {
            switch (inputError.getName()) {
                case "request_body":
                    formError = inputError;
                    break;

                case "course_code":
                    courseCodeError = inputError;
                    break;

                case "semester":
                    semesterError = inputError;
                    break;

                case "session_id":
                    sessionError = inputError;
                    break;
            }
        }
    }
}
