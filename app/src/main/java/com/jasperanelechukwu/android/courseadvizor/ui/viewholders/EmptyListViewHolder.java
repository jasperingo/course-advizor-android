package com.jasperanelechukwu.android.courseadvizor.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderEmptyListBinding;

public class EmptyListViewHolder extends RecyclerView.ViewHolder {
    private final ViewHolderEmptyListBinding binding;

    public EmptyListViewHolder(@NonNull ViewHolderEmptyListBinding viewHolderEmptyListBinding) {
        super(viewHolderEmptyListBinding.getRoot());

        binding = viewHolderEmptyListBinding;
    }

    public void setMessage(@StringRes int message) {
        binding.setMessage(message);
    }
}
