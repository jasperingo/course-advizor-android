package com.jasperanelechukwu.android.courseadvizor;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.jasperanelechukwu.android.courseadvizor.datasources.daos.AppDatabase;

import java.time.LocalDateTime;

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
    @Singleton
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Retrofit provideRetrofit() {
        final Gson gson = new GsonBuilder().registerTypeAdapter(
            LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString())
        ).create();

        return new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    }

    @Provides
    @Singleton
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.NAME)
            .fallbackToDestructiveMigration() //TODO: remove
            .build();
    }

    @Provides
    @Singleton
    public static RxDataStore<Preferences> provideDataStore(@ApplicationContext Context context) {
         return new RxPreferenceDataStoreBuilder(context, "settings").build();
    }
}
