package com.jasperanelechukwu.android.courseadvizor.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class AppointmentEditDialogFragment extends DialogFragment {
    @NonNull
    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(requireContext());
    }
}
