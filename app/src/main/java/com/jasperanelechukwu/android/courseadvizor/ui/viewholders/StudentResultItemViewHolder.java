package com.jasperanelechukwu.android.courseadvizor.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderStudentResultItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.StudentWithResult;

import java.util.function.Consumer;

public class StudentResultItemViewHolder extends RecyclerView.ViewHolder {
    private final ViewHolderStudentResultItemBinding binding;

    public StudentResultItemViewHolder(@NonNull ViewHolderStudentResultItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setStudent(StudentWithResult student) {
        binding.setStudent(student);
    }

    public void setClickListener(Consumer<StudentWithResult> clickListener) {
        binding.setClickListener(clickListener);
    }
}
