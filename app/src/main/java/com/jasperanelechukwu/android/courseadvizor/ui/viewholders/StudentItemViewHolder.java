package com.jasperanelechukwu.android.courseadvizor.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderStudentItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

public class StudentItemViewHolder extends RecyclerView.ViewHolder {
    private final ViewHolderStudentItemBinding binding;

    public StudentItemViewHolder(@NonNull ViewHolderStudentItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setStudent(Student student) {
        binding.setStudent(student);
    }
}
