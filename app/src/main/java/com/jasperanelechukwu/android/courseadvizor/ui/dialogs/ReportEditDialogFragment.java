package com.jasperanelechukwu.android.courseadvizor.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.DialogEditTextViewBinding;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ReportUpdateViewModel;

public class ReportEditDialogFragment extends DialogFragment {
    public static final String ARG_ID_KEY = "reportId";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle arguments = requireArguments();

        final long reportId = arguments.getLong(ARG_ID_KEY);

        final NavController navController = NavHostFragment.findNavController(this);

        final ReportUpdateViewModel viewModel = new ViewModelProvider(requireActivity()).get(ReportUpdateViewModel.class);

        final DialogEditTextViewBinding editTextViewBinding = DialogEditTextViewBinding.inflate(requireActivity().getLayoutInflater());

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
            .setTitle(R.string.reply_to_report)
            .setView(editTextViewBinding.getRoot())
            .setPositiveButton(R.string.done, (dialogInterface, id) -> {})
            .setNegativeButton(R.string.cancel, (dialogInterface, id) -> dismiss())
            .setCancelable(false);

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setOnClickListener(view -> viewModel.updateReport(reportId, editTextViewBinding.customEditTextInput.getText().toString())));

        viewModel.getReportsUiState().observe(this, uiState -> {
            if (uiState.isLoading()) {
                dialog.setContentView(R.layout.view_holder_progress_bar);
            } else if (
                uiState.isSuccess() ||
                uiState.getError() != null ||
                uiState.getFormError() != 0
            ) {
                navController.popBackStack();
                viewModel.resetUpdateReportUiState();
            }
        });

        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
