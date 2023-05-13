package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.Department;

import lombok.Data;

@Data
public class DepartmentDto {
    private int id;

    private String name;

    private String abbreviation;

    @SerializedName(value = "created_at")
    private String createdAt;

    public Department toDepartment() {
        return new Department(id, name, abbreviation, createdAt);
    }
}
