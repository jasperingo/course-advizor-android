package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Getter;

@Getter
public class InvalidCreateStudentResultException extends InvalidFormException {
    private InputError formError;

    private InputError gradeError;

    private InputError studentIdError;

    private InputError resultIdError;

    public InvalidCreateStudentResultException(String message, InputErrorList inputErrors) {
        super(message, inputErrors);

        for(InputError inputError: inputErrors.getData()) {
            switch (inputError.getName()) {
                case "request_body":
                    formError = inputError;
                    break;

                case "student_id":
                    studentIdError = inputError;
                    break;

                case "result_id":
                    resultIdError = inputError;
                    break;

                case "grade":
                    gradeError = inputError;
                    break;
            }
        }
    }
}
