package com.jasperanelechukwu.android.courseadvizor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.jasperanelechukwu.android.courseadvizor.databinding.ActivityProfileBinding;
import com.jasperanelechukwu.android.courseadvizor.ui.fragments.AuthFragment;
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

        binding.setUser(appStore.getCourseAdviser());

        binding.setActivity(this);
    }

    public void signOutClicked() {
        appStore.setCourseAdviser(null);

        Intent intent = new Intent(this, AuthFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}
