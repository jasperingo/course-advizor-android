package com.jasperanelechukwu.android.courseadvizor.ui.fragments.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.entities.Student;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.StudentListAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_students, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            Student student = new Student();
            student.setName("Jasper Anelechukwu");
            student.setImage(R.drawable.ic_students_24);
            students.add(student);
        }

        StudentListAdapter listAdapter = new StudentListAdapter(students);

        RecyclerView recyclerView = view.findViewById(R.id.students_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setAdapter(listAdapter);
    }
}
