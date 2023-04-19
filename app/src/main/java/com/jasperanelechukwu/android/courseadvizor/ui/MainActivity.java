package com.jasperanelechukwu.android.courseadvizor.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jasperanelechukwu.android.courseadvizor.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_nav_host_fragment);

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
        session.setId(12);
        session.setStartedAt(2016);
        session.setEndedAt(2017);
        courseAdviser.setSession(session);
        appStore.setCourseAdviser(courseAdviser);

        if (navHostFragment != null) {
            final NavController navController = navHostFragment.getNavController();

            if (appStore.getCourseAdviser() == null) {
                navController.navigate(R.id.action_global_authFragment);
            }
        }
    }
}

