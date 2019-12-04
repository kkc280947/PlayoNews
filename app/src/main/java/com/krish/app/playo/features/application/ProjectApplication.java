package com.krish.app.playo.features.application;

import android.app.Application;

import com.krish.app.playo.dependencies.components.AppComponent;
import com.krish.app.playo.dependencies.components.DaggerAppComponent;
import com.krish.app.playo.dependencies.modules.AppModule;
import com.krish.app.playo.dependencies.modules.NetworkModule;

import timber.log.Timber;

public class ProjectApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        initDagger();
    }

    private void initDagger() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}

