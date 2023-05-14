package com.jasperanelechukwu.android.courseadvizor.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.jasperanelechukwu.android.courseadvizor.R;

public class StudentResultEditDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.edit_student_result)
            .setSingleChoiceItems(R.array.grades, 0, (dialogInterface, i) -> {

            })
            .setPositiveButton(R.string.done, (dialog, id) -> {

            })
            .setNegativeButton(R.string.cancel, (dialog, id) -> {

            });

        return builder.create();
    }
}
