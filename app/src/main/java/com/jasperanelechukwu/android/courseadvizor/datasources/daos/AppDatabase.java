package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.converters.LocalDateTimeConverter;
import com.jasperanelechukwu.android.courseadvizor.entities.local.AppointmentEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ReportEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.SessionEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.StudentEntity;

@Database(
    version = 4,
    entities = {
        ReportEntity.class,
        StudentEntity.class,
        ResultEntity.class,
        SessionEntity.class,
        AppointmentEntity.class
    }
)
@TypeConverters({LocalDateTimeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String NAME = "course_advizor";

    public abstract StudentDao studentDao();

    public abstract ResultDao resultDao();

    public abstract SessionDao sessionDao();

    public abstract ReportDao reportDao();

    public abstract AppointmentDao appointmentDao();
}
