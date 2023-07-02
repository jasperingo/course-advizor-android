package com.jasperanelechukwu.android.courseadvizor.ui.notification;

import android.app.PendingIntent;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.entities.Appointment;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class AppointmentNotification extends BaseNotification {
    @Inject
    public AppointmentNotification(@ApplicationContext Context context) {
        super(context);
    }

    public void send(final Appointment appointment) {
        PendingIntent pendingIntent = new NavDeepLinkBuilder(getApplicationContext())
            .setGraph(R.navigation.dashboard_nav_graph)
            .setDestination(R.id.navAppointmentsFragment)
            .createPendingIntent();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_appointments_24)
            .setContentTitle("New appointment")
            .setContentText(getApplicationContext().getString(R.string.new_appointment_requested__format, appointment.getStudent().getFullName()))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

       sendNotification(builder, (int) appointment.getId());
    }
}
