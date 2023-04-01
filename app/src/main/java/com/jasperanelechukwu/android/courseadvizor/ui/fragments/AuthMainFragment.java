package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentAuthMainBinding;

public class AuthMainFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAuthMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth_main, container, false);

        binding.setFragment(this);

        return binding.getRoot();
    }

    public void onSignUpButtonClicked(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_navAuthMainFragment_to_navSignUpFragment);
    }

    public void onSignInButtonClicked(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_navAuthMainFragment_to_navSignInFragment);
    }
}
