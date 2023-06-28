package com.jasperanelechukwu.android.courseadvizor.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderAppointmentItemBinding;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderEmptyListBinding;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderErrorBinding;
import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderProgressBarBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.AppointmentsUiState;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.AppointmentItemViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.EmptyListViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.ErrorViewHolder;
import com.jasperanelechukwu.android.courseadvizor.ui.viewholders.ProgressBarViewHolder;

import java.util.function.Consumer;

public class AppointmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_PROGRESS_BAR = 1;
    private static final int VIEW_TYPE_ERROR = 2;
    private static final int VIEW_TYPE_EMPTY_LIST = 3;

    private AppointmentsUiState uiState;

    private final View.OnClickListener onRetryClicked;

    private final Consumer<Appointment> itemClickListener;

    public AppointmentListAdapter(View.OnClickListener onRetryClicked, Consumer<Appointment> itemClickListener) {
        this.onRetryClicked = onRetryClicked;
        this.itemClickListener = itemClickListener;
    }

    public void setUiState(AppointmentsUiState uiState) {
        this.uiState = uiState;

        if (hasExtraOneItem()) {
            notifyItemChanged(0);
        } else {
            notifyItemRemoved(0);
            notifyItemRangeInserted(0, uiState.getAppointments().size());
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
                return new AppointmentItemViewHolder(ViewHolderAppointmentItemBinding.inflate(inflater, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ErrorViewHolder) {
            ((ErrorViewHolder) holder).setError(uiState.getError(), onRetryClicked);
        } else if (holder instanceof EmptyListViewHolder) {
            ((EmptyListViewHolder) holder).setMessage(R.string.no_appointment_found);
        } else if (holder instanceof AppointmentItemViewHolder) {
            AppointmentItemViewHolder itemViewHolder = (AppointmentItemViewHolder) holder;
            itemViewHolder.setClickListener(itemClickListener);
            itemViewHolder.setAppointment(uiState.getAppointments().get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemSize = uiState.getAppointments().size();

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
        int itemSize = uiState.getAppointments().size();

        if (hasExtraOneItem()) {
            return itemSize + 1;
        }

        return itemSize;
    }

    private boolean hasExtraOneItem() {
        return uiState.isLoading() ||
                uiState.getError() != null ||
                (uiState.isLoaded() && uiState.getAppointments().size() == 0);
    }
}
