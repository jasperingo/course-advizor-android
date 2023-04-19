package com.jasperanelechukwu.android.courseadvizor.ui.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentSignInBinding;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.SignInViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SignInViewModel viewModel = new ViewModelProvider(requireParentFragment().requireParentFragment()).get(SignInViewModel.class);

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        viewModel.getSignInUiState().observe(getViewLifecycleOwner(), signInUiState -> {
            if (signInUiState.getError() != null) {
                Toast.makeText(requireContext(), signInUiState.getError(), Toast.LENGTH_LONG).show();
            }

            if (signInUiState.getFormError() != 0) {
                Toast.makeText(requireContext(), signInUiState.getFormError(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
