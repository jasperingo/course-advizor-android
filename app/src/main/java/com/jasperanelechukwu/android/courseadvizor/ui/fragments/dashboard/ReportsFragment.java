package com.jasperanelechukwu.android.courseadvizor.ui.fragments.dashboard;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentReportsBinding;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.ReportListAdapter;
import com.jasperanelechukwu.android.courseadvizor.ui.dialogs.ReportEditDialogFragment;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ReportUpdateViewModel;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ReportsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReportsFragment extends Fragment {
    private FragmentReportsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = NavHostFragment.findNavController(this);

        final ReportsViewModel viewModel = new ViewModelProvider(this).get(ReportsViewModel.class);

        final ReportUpdateViewModel replyViewModel = new ViewModelProvider(requireActivity()).get(ReportUpdateViewModel.class);

        final ReportListAdapter listAdapter = new ReportListAdapter(
            view1 -> viewModel.fetchReports(),
            report -> {
                final Bundle bundle = new Bundle();
                bundle.putLong(ReportEditDialogFragment.ARG_ID_KEY, report.getId());
                navController.navigate(R.id.action_navReportsFragment_to_navReportEditDialog, bundle);
            },
            report -> Toast.makeText(requireContext(), "Play report", Toast.LENGTH_LONG).show()
        );

        viewModel.getReportsUiState().observe(getViewLifecycleOwner(), resultsUiState -> {
            listAdapter.setUiState(resultsUiState);

            if (!resultsUiState.isLoaded() && !resultsUiState.isLoading() && resultsUiState.getError() == null) {
                viewModel.fetchReports();
            }
        });

        replyViewModel.getReportsUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.isSuccess()) {
                Toast.makeText(requireContext(), R.string.report_updated, Toast.LENGTH_LONG).show();
            } else if (uiState.getError() != null) {
                Toast.makeText(requireContext(), uiState.getError(), Toast.LENGTH_LONG).show();
            } else if (uiState.getFormError() != 0) {
                Toast.makeText(requireContext(), uiState.getFormError(), Toast.LENGTH_LONG).show();
            }
        });

        binding.reportsListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.reportsListView.setAdapter(listAdapter);
    }
}
