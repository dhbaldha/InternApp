package com.myinternapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class SharedPrefUtils {

    public static final String CREDENTIALS = "credentials";


    private static SharedPrefUtils mSharedPrefUtils;
    private static SharedPreferences mSharedPreferences;

    public static SharedPrefUtils getSharedPref(Context context){
        if(mSharedPrefUtils == null){
            mSharedPrefUtils = new SharedPrefUtils();
        }
        if(mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return mSharedPrefUtils;
    }

    public void setString(String key, String value){
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public void setInt(String key, int value){
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public void setLong(String key, long value){
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public void setBooleanPreference(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String getString(String key, String defaultValue){
        return mSharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue){
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue){
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public boolean getBooleanPreference(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void removePrefKey(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    public void clearPreference() {
        mSharedPreferences.edit().clear().apply();
    }

    public void saveCredentials(String key, String email, String password){

        Set<String> stringSet = new HashSet<>();
        stringSet.add(email);
        stringSet.add(password);
        mSharedPreferences.edit().putStringSet(key, null).apply();
    }

    public Set<String> getCredentials(String key){
        return mSharedPreferences.getStringSet(key, null);
    }

}
