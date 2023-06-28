package com.jasperanelechukwu.android.courseadvizor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jasperanelechukwu.android.courseadvizor.entities.ui.AppointmentsUiState;
import com.jasperanelechukwu.android.courseadvizor.repositories.AppointmentRepository;
import com.jasperanelechukwu.android.courseadvizor.utils.AppStore;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class AppointmentsViewModel extends ViewModel {
    private final MutableLiveData<AppointmentsUiState> appointmentsUiState = new MutableLiveData<>();

    private final AppointmentRepository appointmentRepository;

    private final AppStore appStore;

    private Disposable appointmentsDisposable;

    @Inject
    public AppointmentsViewModel(AppointmentRepository appointmentRepository, AppStore appStore) {
        this.appStore = appStore;
        this.appointmentRepository = appointmentRepository;
    }

    public LiveData<AppointmentsUiState> getAppointmentsUiState() {
        if (appointmentsUiState.getValue() == null) {
            appointmentsUiState.setValue(new AppointmentsUiState());
        }

        return appointmentsUiState;
    }

    public void fetchAppointments() {
        appointmentsUiState.setValue(new AppointmentsUiState(true));

        appointmentsDisposable = appointmentRepository.getAllAppointments(appStore.getCourseAdviser().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                appointments -> appointmentsUiState.setValue(new AppointmentsUiState(appointments, true)),
                throwable -> appointmentsUiState.setValue(new AppointmentsUiState(throwable.getMessage()))
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
