package com.jasperanelechukwu.android.courseadvizor.exceptions;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.R;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class InvalidFormException extends Exception {
    private final InputErrorList inputErrors;

    public InvalidFormException(String message, InputErrorList inputErrors) {
        super(message);
        this.inputErrors = inputErrors;
    }

    public InputErrorList getInputErrors() {
        return inputErrors;
    }

    @Data
    public static class InputErrorList {
        private List<InputError> data;

        public void addInputInvalid(String name, Object value) {
            add(name, value, InputError.FIELD_INVALID);
        }

        public void addInputRequired(String name, Object value) {
            add(name, value, InputError.FIELD_REQUIRED);
        }

        public void add(String name, Object value, String errorCode) {
            add(name, "", value, errorCode);
        }

        public void add(String name, String message, Object value, String errorCode) {
            if (data == null) {
                data = new ArrayList<>();
            }

            data.add(new InputError(name, message, value, errorCode));
        }

        public boolean hasError() {
            if (data == null) {
                return false;
            }

            return !data.isEmpty();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InputError {
        public final static String FIELD_INVALID = "FIELD_INVALID";

        public final static String FIELD_REQUIRED = "FIELD_REQUIRED";

        public final static String FIELD_PATTERN = "FIELD_PATTERN";

        public final static String FIELD_SIZE = "FIELD_SIZE";

        public final static String FIELD_EXISTS = "FIELD_EXISTS";

        public final static String FIELD_DO_NOT_EXIST = "FIELD_DO_NOT_EXIST";

        public final static String BODY_INVALID = "BODY_INVALID";

        public final static String COURSE_ADVISER_WITH_SESSION_ID_AND_DEPARTMENT_ID_EXISTS = "COURSE_ADVISER_WITH_SESSION_ID_AND_DEPARTMENT_ID_EXISTS";

        public final static String RESULT_WITH_COURSE_CODE_SESSION_ID_AND_SEMESTER_EXISTS = "COURSE_CODE_SESSION_ID_AND_SEMESTER_EXISTS";

        public final static String STUDENT_RESULT_STUDENT_AND_RESULT_ID_EXISTS = "STUDENT_ID_AND_RESULT_ID_FIELD_EXISTS";

        private String name;

        private String message;

        private Object value;

        @SerializedName("error_code")
        private String errorCode;

        public int forInput() {
            if (errorCode == null) {
                return 0;
            }

            switch (errorCode) {
                case COURSE_ADVISER_WITH_SESSION_ID_AND_DEPARTMENT_ID_EXISTS:
                    return R.string.course_adviser_with_session_and_department_already_exists;
                case STUDENT_RESULT_STUDENT_AND_RESULT_ID_EXISTS:
                    return R.string.student_result_with_student_and_result_id_already_exists;
                case RESULT_WITH_COURSE_CODE_SESSION_ID_AND_SEMESTER_EXISTS:
                    return R.string.result_with_course_code_session_id_and_semester_already_exists;
                case FIELD_REQUIRED:
                    return R.string.this_field_is_required;
                case FIELD_DO_NOT_EXIST:
                    return R.string.this_field_do_not_exist;
                case FIELD_EXISTS:
                    return R.string.this_field_already_exists;
                default:
                    return R.string.this_field_is_invalid;
            }
        }
    }
}
