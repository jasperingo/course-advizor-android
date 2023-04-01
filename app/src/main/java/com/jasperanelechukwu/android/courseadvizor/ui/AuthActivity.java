package com.jasperanelechukwu.android.courseadvizor.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.SignUpViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {
    @Inject
    AppStore appStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        final SignUpViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.auth_nav_host_fragment);

        if (navHostFragment != null) {
            final NavController navController = navHostFragment.getNavController();

            signUpViewModel.getNavigateBack().observe(this, aBoolean -> {
                if (aBoolean) {
                    navController.navigateUp();
                }
            });

            signUpViewModel.getCourseAdviser().observe(this, courseAdviser -> {
                if (courseAdviser != null) {
                    appStore.setCourseAdviser(courseAdviser);

                    navController.navigate(R.id.action_global_mainActivity);
                }
            });
        }
    }
}
