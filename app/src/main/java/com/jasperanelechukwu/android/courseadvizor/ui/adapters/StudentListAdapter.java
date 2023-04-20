package com.jasperanelechukwu.android.courseadvizor.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderEmptyListBinding;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderErrorBinding;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderProgressBarBinding;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderStudentItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.StudentsUiState;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.EmptyListViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.ErrorViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.ProgressBarViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.StudentItemViewHolder;

public class StudentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_STUDENT = 0;
    private static final int VIEW_TYPE_PROGRESS_BAR = 1;
    private static final int VIEW_TYPE_ERROR = 2;
    private static final int VIEW_TYPE_EMPTY_LIST = 3;

    private StudentsUiState uiState;

    private final View.OnClickListener onRetryClicked;

    public StudentListAdapter(View.OnClickListener onRetryClicked) {
        this.onRetryClicked = onRetryClicked;
    }

    public void setUiState(StudentsUiState uiState) {
        this.uiState = uiState;

        if (hasExtraOneItem()) {
            notifyItemInserted(0);
        } else {
            notifyItemRangeRemoved(0, 1);
            notifyItemRangeInserted(0, uiState.getStudents().size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_PROGRESS_BAR:
                return new ProgressBarViewHolder(ViewHolderProgressBarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case VIEW_TYPE_ERROR:
                return new ErrorViewHolder(ViewHolderErrorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case VIEW_TYPE_EMPTY_LIST:
                return new EmptyListViewHolder(ViewHolderEmptyListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            default:
                return new StudentItemViewHolder(ViewHolderStudentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ErrorViewHolder) {
            ((ErrorViewHolder) holder).setError(uiState.getError(), onRetryClicked);
        } else if (holder instanceof EmptyListViewHolder) {
            ((EmptyListViewHolder) holder).setMessage(R.string.no_student_found);
        } else if (holder instanceof StudentItemViewHolder) {
            ((StudentItemViewHolder) holder).setStudent(uiState.getStudents().get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemSize = uiState.getStudents().size();

        if (uiState.isLoading() && position == itemSize) {
            return VIEW_TYPE_PROGRESS_BAR;
        }

        if (uiState.getError() != null && position == itemSize) {
            return VIEW_TYPE_ERROR;
        }

        if (uiState.isLoaded() && itemSize == 0) {
            return VIEW_TYPE_EMPTY_LIST;
        }

        return VIEW_TYPE_STUDENT;
    }

    @Override
    public int getItemCount() {
        int itemSize = uiState.getStudents().size();

        if (hasExtraOneItem()) {
            return itemSize + 1;
        }

        return itemSize;
    }

    private boolean hasExtraOneItem() {
        return uiState.isLoading() ||
                uiState.getError() != null ||
                (uiState.isLoaded() && uiState.getStudents().size() == 0);
    }
}
