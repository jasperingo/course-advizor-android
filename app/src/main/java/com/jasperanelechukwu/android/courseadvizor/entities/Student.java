package com.jasperanelechukwu.android.courseadvizor.entities;

import androidx.annotation.DrawableRes;

import lombok.Data;

@Data
public class Student {
    private String name;

    @DrawableRes
    private int image;
}
