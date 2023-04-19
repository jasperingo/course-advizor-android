package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = NavHostFragment.findNavController(this);

        NavHostFragment childNavHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.dashboard_nav_host_fragment);

        if (childNavHostFragment != null) {
            NavController childNavController = childNavHostFragment.getNavController();

            NavigationUI.setupWithNavController(binding.mainBottomNav, childNavController);

            NavigationUI.setupWithNavController(
                binding.toolBar,
                childNavController,
                new AppBarConfiguration.Builder(
                    R.id.navStudentsFragment,
                    R.id.navResultsFragment,
                    R.id.navAppointmentsFragment,
                    R.id.navReportsFragment
                ).build()
            );
        }

        binding.toolBar.inflateMenu(R.menu.menu_tool_bar);

        binding.toolBar.setOnMenuItemClickListener(item -> NavigationUI.onNavDestinationSelected(item, navController));
    }
}
