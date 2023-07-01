package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.jasperanelechukwu.android.courseadvizor.ui.dialogs.StudentResultEditDialogFragment;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ResultViewModel;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.StudentResultModifyViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultFragment extends Fragment {
    public static final String ARG_ID_KEY = "resultId";

    private FragmentResultBinding binding;

    private ResultViewModel viewModel;

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

        final Bundle arguments = requireArguments();

        final long id = arguments.getLong(ARG_ID_KEY);

        binding.setId(id);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        final NavController navController = NavHostFragment.findNavController(this);

        NavigationUI.setupWithNavController(
            binding.toolBar,
            navController,
            new AppBarConfiguration.Builder(navController.getGraph()).build()
        );

        viewModel = new ViewModelProvider(requireActivity()).get(ResultViewModel.class);

        binding.setViewModel(viewModel);

        viewModel.setNoIdErrorMessage(getResources().getString(R.string.no_result_id_set));

        viewModel.getResultUiState().observe(getViewLifecycleOwner(), resultUiState -> {
            if (resultUiState.getResult() == null && !resultUiState.isLoading() && resultUiState.getError() == null) {
                viewModel.fetchResult(id);
            } else if (resultUiState.getResult() != null) {
                viewModel.fetchStudentResults(id);
            }
        });

        new ViewModelProvider(requireActivity()).get(StudentResultModifyViewModel.class)
        .getModifyStudentResultUiState()
        .observe(getViewLifecycleOwner(), modifyStudentResultUiState -> {
            if (modifyStudentResultUiState.getStudentResult() != null) {
                Toast.makeText(requireContext(), R.string.student_result_modified, Toast.LENGTH_LONG).show();
                viewModel.updateStudentResultsAfterModification(modifyStudentResultUiState.getStudentId(), modifyStudentResultUiState.getStudentResult());
            } else if (modifyStudentResultUiState.getError() != null) {
                Toast.makeText(requireContext(), modifyStudentResultUiState.getError(), Toast.LENGTH_LONG).show();
            } else if (modifyStudentResultUiState.getFormError() != 0) {
                Toast.makeText(requireContext(), modifyStudentResultUiState.getFormError(), Toast.LENGTH_LONG).show();
            }
        });

        final StudentResultListAdapter listAdapter = new StudentResultListAdapter(
            view1 -> viewModel.fetchStudentResults(id),
            studentWithResult -> {
                final Bundle bundle = new Bundle();
                bundle.putLong(StudentResultEditDialogFragment.ARG_ID_KEY, studentWithResult.getId());
                navController.navigate(R.id.action_navResultFragment_to_navStudentResultEditDialog, bundle);
            }
        );

        viewModel.getStudentResultsUiState().observe(getViewLifecycleOwner(), listAdapter::setUiState);

        binding.studentsListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.studentsListView.setAdapter(listAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.resetUiStates();
    }
}
