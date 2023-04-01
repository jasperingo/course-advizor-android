package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentSignUpBinding;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.SignUpViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SignUpViewModel viewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        viewModel.getDepartmentsAndSessionsUiState().observe(getViewLifecycleOwner(), departmentsUiState -> {
            if (!departmentsUiState.isLoaded() && !departmentsUiState.isLoading() && departmentsUiState.getError() == null) {
                viewModel.fetchDepartmentsAndSessions();
            }
        });

        viewModel.getSignUpUiState().observe(getViewLifecycleOwner(), signUpUiState -> {
            if (signUpUiState.getError() != null) {
                Toast.makeText(requireContext(), signUpUiState.getError(), Toast.LENGTH_LONG).show();
            }

            if (signUpUiState.getFormError() != 0) {
                Toast.makeText(requireContext(), signUpUiState.getFormError(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
