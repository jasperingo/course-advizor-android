package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jasperanelechukwu.android.courseadvizor.entities.local.ResultEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.SessionEntity;
import com.jasperanelechukwu.android.courseadvizor.entities.local.StudentEntity;

@Database(entities = {StudentEntity.class, ResultEntity.class, SessionEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public static final String NAME = "course_advizor";

    public abstract StudentDao studentDao();

    public abstract ResultDao resultDao();

    public abstract SessionDao sessionDao();
}
