package com.jasperanelechukwu.android.courseadvizor.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.FragmentProfileBinding;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private NavController navController;

    private Disposable signOutDisposable;

    private Disposable fileNameDisposable;

    private final Preferences.Key<String> FILE_NAME = PreferencesKeys.stringKey("avatar_file_name");

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
        new ActivityResultContracts.GetContent(),
        this::onImageSelected
    );

    @Inject
    AppStore appStore;

    @Inject
    AppDatabase appDatabase;

    @Inject
    RxDataStore<Preferences> dataStore;

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

        fileNameDisposable = dataStore.data()
            .map(prefs -> {
                String value = prefs.get(FILE_NAME);
                return value == null ? "" : value;
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fileName -> {
               if (!Objects.equals(fileName, "")) {
                   Bitmap bitmap = BitmapFactory.decodeStream(requireContext().openFileInput(fileName));
                   binding.avatarImageView.setImageBitmap(bitmap);
               }
            }, throwable -> Log.e("Datastore error", null, throwable));
    }

    public void selectImage() {
        mGetContent.launch("image/*");
    }

    public void onImageSelected(final Uri uri) {
        final String fileName = saveFileName(uri);

        try(FileOutputStream fos = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)) {

            final Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            binding.avatarImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String saveFileName(Uri uri) {
        String fileName;

        try (Cursor returnCursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

            returnCursor.moveToFirst();

            fileName = returnCursor.getString(nameIndex);

            dataStore.updateDataAsync(prefsIn -> {
                MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
                mutablePreferences.set(FILE_NAME, fileName);
                return Single.just(mutablePreferences);
            });
        }

        return fileName;
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

        if (fileNameDisposable != null && !fileNameDisposable.isDisposed()) {
            fileNameDisposable.dispose();
        }
    }
}
