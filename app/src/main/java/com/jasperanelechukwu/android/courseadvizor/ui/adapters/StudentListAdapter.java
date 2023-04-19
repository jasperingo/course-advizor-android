package com.jasperanelechukwu.android.courseadvizor.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderStudentItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentItemViewHolder> {
    private final List<Student> students;

    public StudentListAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentItemViewHolder(ViewHolderStudentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentItemViewHolder holder, int position) {
        Student student = students.get(position);
        holder.setStudent(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentItemViewHolder extends RecyclerView.ViewHolder {
        private final ViewHolderStudentItemBinding binding;

        public StudentItemViewHolder(@NonNull ViewHolderStudentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setStudent(Student student) {
            binding.setStudent(student);
        }
    }
}
