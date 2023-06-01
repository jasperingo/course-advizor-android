package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.entities.StudentResult;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidFormException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyStudentResultUiState {
    private StudentResult studentResult;

    private long studentId;

    private boolean loading;

    private String error;

    @StringRes
    private int formError;

    public ModifyStudentResultUiState(StudentResult studentResult, long studentId) {
        this.studentResult = studentResult;
        this.studentId = studentId;
    }

    public ModifyStudentResultUiState(boolean loading) {
        this.loading = loading;
    }

    public ModifyStudentResultUiState(String error) {
        this.error = error;
    }

    public ModifyStudentResultUiState(InvalidFormException.InputError formError) {
        if (formError != null) {
            this.formError = formError.forInput();
        }
    }
}
