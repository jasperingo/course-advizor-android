package com.jasperanelechukwu.android.courseadvizor.ui.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderErrorBinding;

public class ErrorViewHolder extends RecyclerView.ViewHolder {
    private final ViewHolderErrorBinding binding;

    public ErrorViewHolder(@NonNull ViewHolderErrorBinding viewHolderErrorBinding) {
        super(viewHolderErrorBinding.getRoot());

        binding = viewHolderErrorBinding;
    }

    public void setError(String error, View.OnClickListener onRetryClicked) {
        binding.setError(error);
        binding.setRetry(onRetryClicked);
    }
}
