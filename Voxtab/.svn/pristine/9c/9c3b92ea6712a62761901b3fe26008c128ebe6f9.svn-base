package com.voxtab.ariatech.voxtab.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtility {
    private SharedPreferences sharedPref;
    private Editor editor;
    private static final String QUEBAR_PREFERENCES = "voxtab_preferences";

    public SharedPreferencesUtility(Context context) {
        sharedPref = context.getSharedPreferences(QUEBAR_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    public void setString(String key, String value) {
        editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String value) {
        return sharedPref.getString(key, value);
    }

    public String getPreferenceString(String key, String defaultVal) {
        return sharedPref.getString(key, defaultVal);
    }

    public void setBoolean(String key, boolean value) {
        editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getPreferenceBoolean(String key, boolean defaultVal) {
        return sharedPref.getBoolean(key, defaultVal);
    }

    public void setServiceBoolean(String key, boolean value) {
        editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getServiceBoolean(String key, boolean defaultVal) {
        return sharedPref.getBoolean(key, defaultVal);
    }

    public void setInteger(String key, int value) {
        editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInteger(String key, int value) {
        return sharedPref.getInt(key, value);
    }

    public boolean getContained(String key) {
        return sharedPref.contains(key);
    }

    public int getPreferenceInteger(String key, int defaultVal) {
        return sharedPref.getInt(key, defaultVal);
    }
}