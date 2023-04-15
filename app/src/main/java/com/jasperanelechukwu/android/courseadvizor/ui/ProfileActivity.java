package com.jasperanelechukwu.android.courseadvizor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jasperanelechukwu.android.courseadvizor.databinding.ActivityProfileBinding;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {
    @Inject
    AppStore appStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
