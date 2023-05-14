package com.jasperanelechukwu.android.courseadvizor.ui.fragments.dashboard;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentResultsBinding;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.ResultListAdapter;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ResultsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultsFragment extends Fragment {
    private FragmentResultsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController parentNavController = NavHostFragment.findNavController(requireParentFragment().requireParentFragment());

        final ResultsViewModel viewModel = new ViewModelProvider(this).get(ResultsViewModel.class);

        final ResultListAdapter listAdapter = new ResultListAdapter(
            view1 -> viewModel.fetchResults(),
            result -> {
                Bundle bundle = new Bundle();
                bundle.putLong("resultId", result.getId());
                parentNavController.navigate(R.id.action_navDashboardFragment_to_navResultFragment, bundle);
            }
        );

        viewModel.getResultsUiState().observe(getViewLifecycleOwner(), resultsUiState -> {
            listAdapter.setUiState(resultsUiState);

            if (!resultsUiState.isLoaded() && !resultsUiState.isLoading() && resultsUiState.getError() == null) {
                viewModel.fetchResults();
            }
        });

        binding.addResultButton.setOnClickListener(viewB -> parentNavController.navigate(R.id.action_navDashboardFragment_to_navResultCreateFragment));

        binding.resultsListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.resultsListView.setAdapter(listAdapter);
    }
}
