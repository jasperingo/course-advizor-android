package com.jasperanelechukwu.android.courseadvizor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    public enum Semester {
        first, second
    }

    private long id;

    private String courseCode;

    private Semester semester;

    private String createdAt;

    private Session session;
}
