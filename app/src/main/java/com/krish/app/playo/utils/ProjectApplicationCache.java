package com.krish.app.playo.utils;

import android.content.SharedPreferences;

public class ProjectApplicationCache {

    public SharedPreferences mSharedPreferences;

    public ProjectApplicationCache(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }
}
