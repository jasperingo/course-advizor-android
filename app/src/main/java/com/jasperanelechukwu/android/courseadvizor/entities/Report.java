package com.jasperanelechukwu.android.courseadvizor.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private long id;

    private String recordUrl;

    private String reply;

    private LocalDateTime createdAt;

    private Student student;
}
