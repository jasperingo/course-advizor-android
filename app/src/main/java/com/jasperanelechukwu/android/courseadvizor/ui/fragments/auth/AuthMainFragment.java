package com.jasperanelechukwu.android.courseadvizor.ui.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentAuthMainBinding;

public class AuthMainFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAuthMainBinding binding = FragmentAuthMainBinding.inflate(inflater, container, false);

        binding.setFragment(this);

        return binding.getRoot();
    }

    public void onSignUpButtonClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.action_navAuthMainFragment_to_navSignUpFragment);
    }

    public void onSignInButtonClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.action_navAuthMainFragment_to_navSignInFragment);
    }
}
