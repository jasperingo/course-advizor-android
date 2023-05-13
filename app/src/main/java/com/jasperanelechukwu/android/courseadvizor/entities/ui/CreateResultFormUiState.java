package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Result;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;

import lombok.Data;

@Data
public class CreateResultFormUiState {
    private String courseCode;

    private Session session;

    private Result.Semester semester;
}
