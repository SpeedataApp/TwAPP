package com.example.twapp.uitl;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by suntianwei on 2017/4/14.
 */

public class SharedPreferencesUitl {
    private android.content.SharedPreferences sharedPreferences;
    private android.content.SharedPreferences.Editor editor;
    private static SharedPreferencesUitl preferencesUitl = null;


    @SuppressLint("WrongConstant")
    public SharedPreferencesUitl(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_APPEND);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferencesUitl getInstance(Context context, String filename) {
        if (preferencesUitl == null) {
            preferencesUitl = new SharedPreferencesUitl(context, filename);
        }
        return preferencesUitl;
    }

    public void write(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void write(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void write(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public boolean read(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public String read(String key, String defValue) {
        String string = defValue;
        try {
            string = sharedPreferences.getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }
    public int read(String key, int defValue) {
            return sharedPreferences.getInt(key, defValue);
    }
    public void delete(String key) {
        editor.remove(key);
        editor.commit();
    }
}
