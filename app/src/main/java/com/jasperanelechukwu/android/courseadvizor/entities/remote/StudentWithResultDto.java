package com.jasperanelechukwu.android.courseadvizor.entities.remote;

import com.google.gson.annotations.SerializedName;
import com.jasperanelechukwu.android.courseadvizor.entities.StudentResult;
import com.jasperanelechukwu.android.courseadvizor.entities.StudentWithResult;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentWithResultDto extends StudentDto {
    @SerializedName("student_result")
    private List<StudentResultDto> studentResults;

    public StudentWithResult toStudentWithResult() {
        final List<StudentResult> results = new ArrayList<>();

        for (StudentResultDto dto: studentResults) {
            results.add(dto.toStudentResult());
        }

        return new StudentWithResult(getId(), getFirstName(), getLastName(), getMatriculationNumber(), getPhoneNumber(), getCreatedAt(), results);
    }

}
