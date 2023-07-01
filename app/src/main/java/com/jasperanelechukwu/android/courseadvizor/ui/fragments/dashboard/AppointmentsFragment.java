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
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentAppointmentsBinding;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.AppointmentListAdapter;
import com.jasperanelechukwu.android.courseadvizor.ui.dialogs.AppointmentEditDialogFragment;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.AppointmentUpdateViewModel;
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

        final NavController navController = NavHostFragment.findNavController(this);

        final AppointmentsViewModel viewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);

        final AppointmentUpdateViewModel updateViewModel = new ViewModelProvider(requireActivity()).get(AppointmentUpdateViewModel.class);

        final AppointmentListAdapter listAdapter = new AppointmentListAdapter(
            view1 -> viewModel.fetchAppointments(),
            appointment -> {
                final Bundle bundle = new Bundle();
                bundle.putLong(AppointmentEditDialogFragment.ARG_ID_KEY, appointment.getId());
                navController.navigate(R.id.action_navAppointmentsFragment_to_navAppointmentEditDialog, bundle);
            }
        );

        viewModel.getAppointmentsUiState().observe(getViewLifecycleOwner(), resultsUiState -> {
            listAdapter.setUiState(resultsUiState);

            if (!resultsUiState.isLoaded() && !resultsUiState.isLoading() && resultsUiState.getError() == null) {
                viewModel.fetchAppointments();
            }
        });

        updateViewModel.getAppointmentsUiState().observe(getViewLifecycleOwner(), updateAppointmentUiState -> {
            if (updateAppointmentUiState.isSuccess()) {
                Toast.makeText(requireContext(), R.string.appointment_updated, Toast.LENGTH_LONG).show();
            } else if (updateAppointmentUiState.getError() != null) {
                Toast.makeText(requireContext(), updateAppointmentUiState.getError(), Toast.LENGTH_LONG).show();
            } else if (updateAppointmentUiState.getFormError() != 0) {
                Toast.makeText(requireContext(), updateAppointmentUiState.getFormError(), Toast.LENGTH_LONG).show();
            }
        });

        binding.appointmentsListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.appointmentsListView.setAdapter(listAdapter);
    }
}
