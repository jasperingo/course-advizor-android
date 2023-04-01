package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Department;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;

import lombok.Data;

@Data
public class SignUpFormUiState {
    private String pin;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Department department;

    private Session session;
}
