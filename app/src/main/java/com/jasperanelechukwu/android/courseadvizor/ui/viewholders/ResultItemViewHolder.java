package com.jasperanelechukwu.android.courseadvizor.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderResultItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.Result;

import java.util.function.Consumer;

public class ResultItemViewHolder extends RecyclerView.ViewHolder {
    private final ViewHolderResultItemBinding binding;

    public ResultItemViewHolder(@NonNull ViewHolderResultItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setResult(Result result) {
        binding.setResult(result);
    }

    public void setClickListener(Consumer<Result> clickListener) {
        binding.setClickListener(clickListener);
    }
}
