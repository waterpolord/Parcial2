package com.proyectoclase.segundoparcialrg.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceStorage {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceStorage(Context context) {
        preferences = context.getSharedPreferences("credenciales", 0);
        editor = preferences.edit();
    }
    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public void putBool(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public String getString(String key) {
        return preferences.getString(key, "");
    }
    public Boolean getBool(String key) {
        return preferences.getBoolean(key, false);
    }
    public float getNumber(String key) {
        return preferences.getFloat(key, 0);
    }


}
