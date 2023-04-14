package com.jasperanelechukwu.android.courseadvizor.exceptions;

import lombok.Getter;

@Getter
public class InvalidSignInFormException extends InvalidFormException {
    private InputError formError;

    private InputError pinError;

    private InputError phoneNumberError;

    public InvalidSignInFormException(String message, InputErrorList inputErrors) {
        super(message, inputErrors);

        for(InputError inputError: inputErrors.getData()) {
            switch (inputError.getName()) {
                case "request_body":
                    formError = inputError;
                    break;

                case "phone_number":
                    phoneNumberError = inputError;
                    break;

                case "pin":
                    pinError = inputError;
                    break;
            }
        }
    }
}
