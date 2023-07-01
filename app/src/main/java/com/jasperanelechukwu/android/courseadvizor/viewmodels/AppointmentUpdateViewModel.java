package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.UpdateAppointmentFormUiState;
import com.jasperanelechukwu.android.courseadvizor.entities.ui.UpdateAppointmentUiState;
import com.jasperanelechukwu.android.courseadvizor.exceptions.InvalidUpdateAppointmentException;
import com.jasperanelechukwu.android.courseadvizor.repositories.AppointmentRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import java.time.LocalDateTime;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class AppointmentUpdateViewModel extends ViewModel {
    private final UpdateAppointmentFormUiState updateAppointmentFormUiState = new UpdateAppointmentFormUiState();

    private final MutableLiveData<UpdateAppointmentUiState> appointmentsUiState = new MutableLiveData<>();

    private final AppointmentRepository appointmentRepository;

    private final AppStore appStore;

    private Disposable appointmentsDisposable;

    @Inject
    public AppointmentUpdateViewModel(AppointmentRepository appointmentRepository, AppStore appStore) {
        this.appStore = appStore;
        this.appointmentRepository = appointmentRepository;
    }

    public LiveData<UpdateAppointmentUiState> getAppointmentsUiState() {
        if (appointmentsUiState.getValue() == null) {
            appointmentsUiState.setValue(new UpdateAppointmentUiState());
        }

        return appointmentsUiState;
    }

    public void resetUpdateAppointmentUiState() {
        appointmentsUiState.setValue(new UpdateAppointmentUiState());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setStartAt(int year, int month, int day, int hour, int minute) {
        updateAppointmentFormUiState.setStartedAt(LocalDateTime.of(year, ++month, day, hour, minute).toString());
    }

    public void updateAppointment(final long appointmentId) {
        appointmentsUiState.setValue(new UpdateAppointmentUiState(true));

        appointmentsDisposable = appointmentRepository.update(appointmentId, updateAppointmentFormUiState, appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                appointment -> appointmentsUiState.setValue(new UpdateAppointmentUiState(appointment)),
                throwable -> {
                    if (throwable instanceof InvalidUpdateAppointmentException) {
                        final InvalidUpdateAppointmentException exception = (InvalidUpdateAppointmentException) throwable;

                        appointmentsUiState.setValue(new UpdateAppointmentUiState(
                            exception.getStatusError() != null
                                ? exception.getStatusError()
                                : exception.getStartedAtError() != null
                                    ? exception.getStartedAtError()
                                    : exception.getFormError()
                        ));
                    } else {
                        appointmentsUiState.setValue(new UpdateAppointmentUiState(throwable.getMessage()));
                    }
                }
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (appointmentsDisposable != null && !appointmentsDisposable.isDisposed()) {
            appointmentsDisposable.dispose();
        }
    }
}
