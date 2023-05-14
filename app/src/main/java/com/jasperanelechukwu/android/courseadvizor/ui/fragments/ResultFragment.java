package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentResultBinding;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.StudentResultListAdapter;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ResultViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultFragment extends Fragment {
    public static final String ARG_ID_KEY = "resultId";

    private FragmentResultBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = NavHostFragment.findNavController(this);

        NavigationUI.setupWithNavController(
            binding.toolBar,
            navController,
            new AppBarConfiguration.Builder(navController.getGraph()).build()
        );

        final ResultViewModel viewModel = new ViewModelProvider(this).get(ResultViewModel.class);

        viewModel.setNoIdErrorMessage(getResources().getString(R.string.no_result_id_set));

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        final Bundle arguments = getArguments();

        if (arguments == null) {
            viewModel.fetchResult(0);
            binding.setId(0L);
            return;
        }

        final long id = arguments.getLong(ARG_ID_KEY);

        binding.setId(id);

        viewModel.getResultUiState().observe(getViewLifecycleOwner(), resultUiState -> {
            if (resultUiState.getResult() == null && !resultUiState.isLoading() && resultUiState.getError() == null) {
                viewModel.fetchResult(id);
            } else if (resultUiState.getResult() != null) {
                viewModel.fetchStudentResults(id);
            }
        });

        final StudentResultListAdapter listAdapter = new StudentResultListAdapter(
            view1 -> viewModel.fetchStudentResults(id),
            studentWithResult -> navController.navigate(R.id.action_navResultFragment_to_navStudentResultEditDialog)
        );

        viewModel.getStudentResultsUiState().observe(getViewLifecycleOwner(), listAdapter::setUiState);

        binding.studentsListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.studentsListView.setAdapter(listAdapter);
    }
}
