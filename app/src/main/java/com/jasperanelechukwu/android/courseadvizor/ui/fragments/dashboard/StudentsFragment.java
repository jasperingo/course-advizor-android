package com.jasperanelechukwu.android.courseadvizor.ui.fragments.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.StudentListAdapter;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.StudentsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StudentsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_students, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final StudentsViewModel viewModel = new ViewModelProvider(this).get(StudentsViewModel.class);

        final StudentListAdapter listAdapter = new StudentListAdapter(view1 -> viewModel.fetchStudents());

        viewModel.getStudentsUiState().observe(getViewLifecycleOwner(), studentsUiState -> {
            listAdapter.setUiState(studentsUiState);

            if (!studentsUiState.isLoaded() && !studentsUiState.isLoading() && studentsUiState.getError() == null) {
                viewModel.fetchStudents();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.students_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setAdapter(listAdapter);
    }
}
