package com.jasperanelechukwu.android.courseadvizor.ui.notification;

import android.app.PendingIntent;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.entities.Report;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class ReportNotification extends BaseNotification {
    @Inject
    public ReportNotification(@ApplicationContext Context context) {
        super(context);
    }

    public void send(final Report report) {
        PendingIntent pendingIntent = new NavDeepLinkBuilder(getApplicationContext())
            .setGraph(R.navigation.dashboard_nav_graph)
            .setDestination(R.id.navReportsFragment)
            .createPendingIntent();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_reports_24)
            .setContentTitle(getApplicationContext().getString(R.string.new_report))
            .setContentText(getApplicationContext().getString(R.string.new_report_requested__format, report.getStudent().getFullName()))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

       sendNotification(builder, (int) report.getId());
    }
}
