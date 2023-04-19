package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.SignInViewModel;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.SignUpViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthFragment extends Fragment {
    @Inject
    AppStore appStore;

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        final SignUpViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        final SignInViewModel signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        signUpViewModel.getCourseAdviser().observe(getViewLifecycleOwner(), this::storeCourseAdviser);

        signInViewModel.getCourseAdviser().observe(getViewLifecycleOwner(), this::storeCourseAdviser);

        final Toolbar toolbar = view.findViewById(R.id.tool_bar);

        final NavHostFragment childNavHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.auth_nav_host_fragment);

        if (childNavHostFragment != null) {
            final NavController childNavController = childNavHostFragment.getNavController();

            NavigationUI.setupWithNavController(
                toolbar,
                childNavController,
                new AppBarConfiguration.Builder(childNavController.getGraph()).build()
            );

            childNavController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
                if (navDestination.getId() == R.id.navAuthMainFragment) {
                    toolbar.setVisibility(View.GONE);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void storeCourseAdviser(CourseAdviser courseAdviser) {
        if (courseAdviser != null) {
            appStore.setCourseAdviser(courseAdviser);

            navController.navigate(R.id.action_navAuthFragment_to_navStudentsFragment);
        }
    }
}
