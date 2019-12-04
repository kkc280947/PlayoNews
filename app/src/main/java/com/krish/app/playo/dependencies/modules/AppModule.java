package com.krish.app.playo.dependencies.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.krish.app.playo.features.application.ProjectApplication;
import com.krish.app.playo.utils.ProjectApplicationCache;
import com.krish.app.playo.utils.ProjectApplicationConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final ProjectApplication mProjectApplication;

    public AppModule(ProjectApplication projectApplication) {
        mProjectApplication = projectApplication;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return mProjectApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference(Context context) {
        return context.getSharedPreferences(ProjectApplicationConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    ProjectApplicationCache provideProjectApplicationCache(SharedPreferences sharedPreferences) {
        return new ProjectApplicationCache(sharedPreferences);
    }
}
