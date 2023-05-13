package com.jasperanelechukwu.android.courseadvizor.entities.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Student")
public class StudentEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String firstName;

    private String lastName;

    private String matriculationNumber;

    private String phoneNumber;

    private String createdAt;

    public Student toStudent() {
        return new Student(id, firstName, lastName, matriculationNumber, phoneNumber, createdAt);
    }
}
