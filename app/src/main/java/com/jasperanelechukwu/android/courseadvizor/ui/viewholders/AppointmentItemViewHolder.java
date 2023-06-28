package com.jasperanelechukwu.android.courseadvizor.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasperanelechukwu.android.courseadvizor.databinding.ViewHolderAppointmentItemBinding;
import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;

public class AppointmentItemViewHolder extends RecyclerView.ViewHolder {
    private final ViewHolderAppointmentItemBinding binding;

    public AppointmentItemViewHolder(@NonNull ViewHolderAppointmentItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setAppointment(Appointment appointment) {
        binding.setAppointment(appointment);
    }

//    public void setClickListener(Consumer<Result> clickListener) {
//        binding.setClickListener(clickListener);
//    }
}
