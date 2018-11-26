package com.spg.sgpco.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class PreferencesData {


    public static String getToken(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("token", "");
    }


    public static void saveToken(Context context, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("token", "Bearer " + value).apply();
    }


    public static void saveString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    public static void isLogin(Context context, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isLogin", value).apply();
    }

    public static boolean getIsLogin(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isLogin", false);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

//    public static void saveString(String key, String value) {
//        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString(key, value).commit();
//    }
//
//    public static void saveFloat(String key, Float value) {
//        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putFloat(key, value.floatValue()).commit();
//    }
//

//

//    public static void saveBoolean(String key, boolean value) {
//        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(key, value).apply();
//    }
//
//    public static int getInt(String key) {
//        return PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(key, 0);
//    }
//

//
//    public static String getString(String key, String defaultValue) {
//        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, defaultValue);
//    }
//
//    public static String getString(String key) {
//        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, "");
//    }
//
//    public static boolean getBoolean(String key, boolean defaultValue) {
//        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(key, defaultValue);
//    }
//
//    public static float getFloat(String key, float defaultValue) {
//        return PreferenceManager.getDefaultSharedPreferences(getContext()).getFloat(key, defaultValue);
//    }
//
//    public static long getLong(String key, long defaultValue) {
//        return PreferenceManager.getDefaultSharedPreferences(getContext()).getLong(key, defaultValue);
//    }


}
