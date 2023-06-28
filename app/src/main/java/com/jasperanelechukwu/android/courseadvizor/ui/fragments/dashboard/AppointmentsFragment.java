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

import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentAppointmentsBinding;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.AppointmentListAdapter;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.AppointmentsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AppointmentsFragment extends Fragment {
    private FragmentAppointmentsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController parentNavController = NavHostFragment.findNavController(requireParentFragment().requireParentFragment());

        final AppointmentsViewModel viewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);

        final AppointmentListAdapter listAdapter = new AppointmentListAdapter(
            view1 -> viewModel.fetchAppointments(),
            appointment -> {}
        );

        viewModel.getAppointmentsUiState().observe(getViewLifecycleOwner(), resultsUiState -> {
            listAdapter.setUiState(resultsUiState);

            if (!resultsUiState.isLoaded() && !resultsUiState.isLoading() && resultsUiState.getError() == null) {
                viewModel.fetchAppointments();
            }
        });

        binding.appointmentsListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.appointmentsListView.setAdapter(listAdapter);
    }
}
