package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentProfileBinding;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private NavController navController;

    private Disposable signOutDisposable;

    @Inject
    AppStore appStore;

    @Inject
    AppDatabase appDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setFragment(this);

        binding.setUser(appStore.getCourseAdviser());

        navController = NavHostFragment.findNavController(this);

        NavigationUI.setupWithNavController(
            binding.toolBar,
            navController,
            new AppBarConfiguration.Builder(navController.getGraph()).build()
        );
    }

    public void signOutClicked() {
        appStore.setCourseAdviser(null);
        signOutDisposable = Completable.fromAction(() -> appDatabase.clearAllTables())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> navController.navigate(R.id.action_global_authFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (signOutDisposable != null && !signOutDisposable.isDisposed()) {
            signOutDisposable.dispose();
        }
    }
}
