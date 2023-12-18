package com.example.dagger_hilt.data.datasource.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dagger_hilt.sys.util.Constants;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class Storage {

    @Inject
    @ApplicationContext
    Context app;

    @Inject
    public Storage() {
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor =
                app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return
                app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE).getString(key, "");
    }
}
