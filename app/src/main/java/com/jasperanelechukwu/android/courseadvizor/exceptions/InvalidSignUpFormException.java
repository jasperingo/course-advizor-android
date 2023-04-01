package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Getter;

@Getter
public class InvalidSignUpFormException extends InvalidFormException {
    private InputError formError;

    private InputError pinError;

    private InputError firstNameError;

    private InputError lastNameError;

    private InputError phoneNumberError;

    private InputError departmentError;

    private InputError sessionError;

    public InvalidSignUpFormException(String message, InputErrorList inputErrors) {
        super(message, inputErrors);

        for(InputError inputError: inputErrors.getData()) {
            switch (inputError.getName()) {
                case "request_body":
                    formError = inputError;
                    break;

                case "phone_number":
                    phoneNumberError = inputError;
                    break;

                case "first_name":
                    firstNameError = inputError;
                    break;

                case "last_name":
                    lastNameError = inputError;
                    break;

                case "department_id":
                    departmentError = inputError;
                    break;

                case "session_id":
                    sessionError = inputError;
                    break;

                case "pin":
                    pinError = inputError;
                    break;
            }
        }
    }
}
