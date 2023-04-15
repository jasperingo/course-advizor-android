package com.jasperanelechukwu.android.courseadvizor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.ActivityMainBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.CourseAdviser;
import com.jasperanelechukwu.android.courseadvizor.entities.Department;
import com.jasperanelechukwu.android.courseadvizor.entities.Session;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    AppStore appStore;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolBar;

        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = binding.mainBottomNav;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            NavigationUI.setupWithNavController(bottomNav, navController);

            NavigationUI.setupWithNavController(
                toolbar,
                navController,
                new AppBarConfiguration.Builder(
                    R.id.navStudentsFragment,
                    R.id.navResultsFragment,
                    R.id.navAppointmentsFragment,
                    R.id.navReportsFragment
                ).build()
            );
        }

        CourseAdviser courseAdviser = new CourseAdviser();
        courseAdviser.setId(1L);
        courseAdviser.setFirstName("Jasper");
        courseAdviser.setLastName("Anelechukwu");
        courseAdviser.setPhoneNumber("+2349030572411");
        Department department = new Department();
        department.setName("Information Technology");
        department.setAbbreviation("IFT");
        courseAdviser.setDepartment(department);
        Session session = new Session();
        session.setStartedAt(2016);
        session.setEndedAt(2017);
        courseAdviser.setSession(session);
        appStore.setCourseAdviser(courseAdviser);

        if (appStore.getCourseAdviser() == null && navController != null) {
            navController.navigate(R.id.action_global_authActivity);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}
