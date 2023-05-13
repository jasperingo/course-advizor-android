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

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentResultCreateBinding;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ResultCreateViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultCreateFragment extends Fragment {
    private FragmentResultCreateBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultCreateBinding.inflate(inflater, container, false);
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

        final ResultCreateViewModel viewModel = new ViewModelProvider(this).get(ResultCreateViewModel.class);

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        viewModel.getSessionsUiState().observe(getViewLifecycleOwner(), sessionsUiState -> {
            if (!sessionsUiState.isLoaded() && !sessionsUiState.isLoading() && sessionsUiState.getError() == null) {
                viewModel.fetchSessions();
            }
        });

        viewModel.getCreateResultUiState().observe(getViewLifecycleOwner(), createResultUiState -> {
            if (createResultUiState.getError() != null) {
                Toast.makeText(requireContext(), createResultUiState.getError(), Toast.LENGTH_LONG).show();
            }

            if (createResultUiState.getFormError() != 0) {
                Toast.makeText(requireContext(), createResultUiState.getFormError(), Toast.LENGTH_LONG).show();
            }

            if (createResultUiState.getResult() != null) {
                Bundle bundle = new Bundle();
                bundle.putLong("resultId", createResultUiState.getResult().getId());
                navController.navigate(R.id.action_navResultCreateFragment_to_navResultFragment, bundle);
            }
        });
    }
}
