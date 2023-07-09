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
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderReportItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.Report;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.ReportsUiState;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.EmptyListViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.ErrorViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.ProgressBarViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.ReportItemViewHolder;

import java.util.function.Consumer;

public class ReportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_PROGRESS_BAR = 1;
    private static final int VIEW_TYPE_ERROR = 2;
    private static final int VIEW_TYPE_EMPTY_LIST = 3;

    private ReportsUiState uiState;

    private final View.OnClickListener onRetryClicked;

    private final Consumer<Report> replyClickListener;

    private final Consumer<Report> playClickListener;

    public ReportListAdapter(View.OnClickListener onRetryClicked, Consumer<Report> replyClickListener, Consumer<Report> playClickListener) {
        this.onRetryClicked = onRetryClicked;
        this.replyClickListener = replyClickListener;
        this.playClickListener = playClickListener;
    }

    public void setUiState(ReportsUiState uiState) {
        final boolean modified = this.uiState != null;

        this.uiState = uiState;

        if (hasExtraOneItem()) {
            notifyItemChanged(0);
        } else if (modified) {
            notifyItemRangeChanged(0, uiState.getReports().size());
        } else {
            notifyItemRemoved(0);
            notifyItemRangeInserted(0, uiState.getReports().size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_TYPE_PROGRESS_BAR:
                return new ProgressBarViewHolder(ViewHolderProgressBarBinding.inflate(inflater, parent, false));
            case VIEW_TYPE_ERROR:
                return new ErrorViewHolder(ViewHolderErrorBinding.inflate(inflater, parent, false));
            case VIEW_TYPE_EMPTY_LIST:
                return new EmptyListViewHolder(ViewHolderEmptyListBinding.inflate(inflater, parent, false));
            default:
                return new ReportItemViewHolder(ViewHolderReportItemBinding.inflate(inflater, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ErrorViewHolder) {
            ((ErrorViewHolder) holder).setError(uiState.getError(), onRetryClicked);
        } else if (holder instanceof EmptyListViewHolder) {
            ((EmptyListViewHolder) holder).setMessage(R.string.no_appointment_found);
        } else if (holder instanceof ReportItemViewHolder) {
            ReportItemViewHolder itemViewHolder = (ReportItemViewHolder) holder;
            itemViewHolder.setReplyListener(replyClickListener);
            itemViewHolder.setPlayListener(playClickListener);
            itemViewHolder.setReport(uiState.getReports().get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemSize = uiState.getReports().size();

        if (uiState.isLoading() && position == itemSize) {
            return VIEW_TYPE_PROGRESS_BAR;
        }

        if (uiState.getError() != null && position == itemSize) {
            return VIEW_TYPE_ERROR;
        }

        if (uiState.isLoaded() && itemSize == 0) {
            return VIEW_TYPE_EMPTY_LIST;
        }

        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        int itemSize = uiState.getReports().size();

        if (hasExtraOneItem()) {
            return itemSize + 1;
        }

        return itemSize;
    }

    private boolean hasExtraOneItem() {
        return uiState.isLoading() ||
                uiState.getError() != null ||
                (uiState.isLoaded() && uiState.getReports().size() == 0);
    }
}
