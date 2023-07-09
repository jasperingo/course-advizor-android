package com.jasperanelechukwu.android.courseadvizor.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderReportItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.Report;

import java.util.function.Consumer;

public class ReportItemViewHolder extends RecyclerView.ViewHolder {
    private final ViewHolderReportItemBinding binding;

    public ReportItemViewHolder(@NonNull ViewHolderReportItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setReport(Report report) {
        binding.setReport(report);
    }

    public void setReplyListener(Consumer<Report> replyListener) {
        binding.setReplyListener(replyListener);
    }

    public void setPlayListener(Consumer<Report> playListener) {
        binding.setPlayListener(playListener);
    }
}
