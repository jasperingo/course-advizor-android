package com.jasperanelechukwu.android.courseadvizor.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.DialogDateTimePickerBinding;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.AppointmentUpdateViewModel;

import java.util.concurrent.atomic.AtomicInteger;

public class AppointmentEditDialogFragment extends DialogFragment {
    public static final String ARG_ID_KEY = "appointmentId";

    @NonNull
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle arguments = requireArguments();

        final long appointmentId = arguments.getLong(ARG_ID_KEY);

        final NavController navController = NavHostFragment.findNavController(this);

        final AppointmentUpdateViewModel updateViewModel = new ViewModelProvider(requireActivity()).get(AppointmentUpdateViewModel.class);

        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final DialogDateTimePickerBinding dateTimePickerBinding = DialogDateTimePickerBinding.inflate(inflater);

        final AtomicInteger year = new AtomicInteger(dateTimePickerBinding.datePicker.getYear());
        final AtomicInteger month = new AtomicInteger(dateTimePickerBinding.datePicker.getMonth());
        final AtomicInteger day = new AtomicInteger(dateTimePickerBinding.datePicker.getDayOfMonth());
        final AtomicInteger hour = new AtomicInteger(dateTimePickerBinding.timePicker.getHour());
        final AtomicInteger minute = new AtomicInteger(dateTimePickerBinding.timePicker.getMinute());

        dateTimePickerBinding.datePicker.setOnDateChangedListener((datePicker, mYear, mMonth, mDay) -> {
            year.set(mYear);
            month.set(mMonth);
            day.set(mDay);
        });

        dateTimePickerBinding.timePicker.setOnTimeChangedListener((timePicker, mHour, mMinute) -> {
            hour.set(mHour);
            minute.set(mMinute);
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
            .setView(dateTimePickerBinding.getRoot())
            .setCancelable(false);

        final AlertDialog dialog = builder.create();

        dialog.setCanceledOnTouchOutside(false);

        dateTimePickerBinding.doneButton.setOnClickListener(view -> {
            if (dateTimePickerBinding.datePicker.getVisibility() == View.VISIBLE) {
                dateTimePickerBinding.doneButton.setText(R.string.done);
                dateTimePickerBinding.datePicker.setVisibility(View.GONE);
                dateTimePickerBinding.timePicker.setVisibility(View.VISIBLE);
            } else {
                updateViewModel.setStartAt(year.get(), month.get(), day.get(), hour.get(), minute.get());
                updateViewModel.updateAppointment(appointmentId);
            }
        });

        dateTimePickerBinding.cancelButton.setOnClickListener(view -> dismiss());

        updateViewModel.getAppointmentsUiState().observe(this, updateAppointmentUiState -> {
            if (updateAppointmentUiState.isLoading()) {
                dialog.setContentView(R.layout.view_holder_progress_bar);
            } else if (
                updateAppointmentUiState.isSuccess() ||
                updateAppointmentUiState.getError() != null ||
                updateAppointmentUiState.getFormError() != 0
            ) {
                navController.popBackStack();
                updateViewModel.resetUpdateAppointmentUiState();
            }
        });

        return dialog;
    }
}
