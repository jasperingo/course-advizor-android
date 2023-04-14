package com.jasperanelechukwu.android.courseadvizor.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.SignInViewModel;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.SignUpViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {
    @Inject
    AppStore appStore;

    @Nullable
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        final SignUpViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        final SignInViewModel signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.auth_nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        signUpViewModel.getNavigateBack().observe(this, this::navigateUpFromFragments);

        signInViewModel.getNavigateBack().observe(this, this::navigateUpFromFragments);

        signUpViewModel.getCourseAdviser().observe(this, this::storeCourseAdviser);

        signInViewModel.getCourseAdviser().observe(this, this::storeCourseAdviser);
    }

    private void navigateUpFromFragments(boolean aBoolean) {
        if (aBoolean && navController != null) {
            navController.navigateUp();
        }
    }

    private void storeCourseAdviser(CourseAdviser courseAdviser) {
        if (courseAdviser != null) {
            appStore.setCourseAdviser(courseAdviser);

            finish();
        }
    }
}
