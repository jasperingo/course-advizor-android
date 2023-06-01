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
import com.jasperanelechukwu.android.courseadvizor.entities.StudentWithResult;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ResultViewModel;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.StudentResultModifyViewModel;

import java.util.Objects;

public class StudentResultEditDialogFragment extends DialogFragment {
    public static final String ARG_ID_KEY = "studentId";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle arguments = requireArguments();

        final long studentId = arguments.getLong(ARG_ID_KEY);

        final String[] grades = getResources().getStringArray(R.array.grades);

        final NavController navController = NavHostFragment.findNavController(this);

        final ResultViewModel viewModel = new ViewModelProvider(requireActivity()).get(ResultViewModel.class);

        final StudentResultModifyViewModel modifyViewModel = new ViewModelProvider(requireActivity()).get(StudentResultModifyViewModel.class);

        modifyViewModel.setStudentId(studentId);

        modifyViewModel.setResultId(Objects.requireNonNull(viewModel.getResultUiState().getValue()).getResult().getId());

        StudentWithResult student = null;

        for (StudentWithResult studentR: Objects.requireNonNull(viewModel.getStudentResultsUiState().getValue()).getStudents()) {
            if (studentR.getId() == studentId) {
                student = studentR;
                break;
            }
        }

        int gradeIndex = -1;

        if (student != null && student.getStudentResults().size() > 0) {
            for (int i = 0; i < grades.length; i++) {
                if (Objects.equals(grades[i], student.getStudentResults().get(0).getGrade().name())) {
                    gradeIndex = i;
                }
            }
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
            .setTitle(R.string.edit_student_result)
            .setSingleChoiceItems(R.array.grades, gradeIndex, (dialogInterface, i) -> modifyViewModel.setGrade(grades[i]))
            .setPositiveButton(R.string.done, (dialogInterface, id) -> {})
            .setNegativeButton(R.string.cancel, (dialogInterface, id) -> dismiss())
            .setCancelable(false);

        final AlertDialog dialog = builder.create();

        final StudentWithResult finalStudent = Objects.requireNonNull(student);

        dialog.setOnShowListener(dialogInterface -> dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
            if (finalStudent.getStudentResults().size() > 0) {
                modifyViewModel.updateStudentResult(finalStudent.getStudentResults().get(0).getId());
            } else {
                modifyViewModel.createStudentResult();
            }
        }));

        modifyViewModel.getModifyStudentResultUiState().observe(this, modifyStudentResultUiState -> {
            if (modifyStudentResultUiState.isLoading()) {
                dialog.setContentView(R.layout.view_holder_progress_bar);
            } else if (
                modifyStudentResultUiState.getStudentResult() != null ||
                modifyStudentResultUiState.getError() != null ||
                modifyStudentResultUiState.getFormError() != 0
            ) {
                navController.popBackStack();
                modifyViewModel.resetModifyStudentResultUiState();
            }
        });

        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
