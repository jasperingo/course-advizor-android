package com.jasperanelechukwu.android.courseadvizor.datasources.daos.converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalDateTimeConverter {
    @TypeConverter
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime fromTimestamp(Long value) {
        return value == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
    }

    @TypeConverter
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Long localDateTimeToTimestamp(LocalDateTime date) {
        return date == null ? null : date.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
