package com.jasperanelechukwu.android.courseadvizor.datasources.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jasperanelechukwu.android.courseadvizor.entities.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String NAME = "course_advizor";

    public abstract StudentDao studentDao();
}
