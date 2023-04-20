package com.jasperanelechukwu.android.courseadvizor;

import android.content.Context;

import androidx.room.Room;

import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ApplicationModule {
    @Provides
    public static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    @Singleton
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "course_advizor")
                .fallbackToDestructiveMigration() //TODO: remove
                .build();
    }
}
