package com.example.sgpco.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class PreferencesData {


    public static int getCasheVersion(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("cacheVersion", 0);
    }


    public static void saveCacheVersion(Context context, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("cacheVersion", value).apply();
    }

    public static void saveCityId(Context context, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("cityId", value).apply();
    }

    public static int getCityId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("cityId", 0);
    }

    public static void saveDepositRegisterPrice(Context context, int value) {
        if (value <= 0) {
            value = 20000;
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("depositRegisterPrice", value).apply();
    }

    public static int getDepositRegisterPrice(Context context) {

        int price = PreferenceManager.getDefaultSharedPreferences(context).getInt("depositRegisterPrice", 20000);
        if (price > 0) {
            return price;
        }
        return 20000;
    }

    public static void saveDemandRegisterPrice(Context context, int value) {
        if (value <= 0) {
            value = 50000;
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("demandRegisterPrice", value).apply();
    }

    public static int getDemandRegisterPrice(Context context) {

        int price = PreferenceManager.getDefaultSharedPreferences(context).getInt("demandRegisterPrice", 50000);
        if (price > 0) {
            return price;
        }
        return 50000;
    }

    public static void saveMaxImageCount(Context context, int value) {
        if (value < 0 || value > 30) {
            value = 8;
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("depositRegisterImageCount", value).apply();
    }

    public static int getMaxImageCount(Context context) {
        int imageCount = PreferenceManager.getDefaultSharedPreferences(context).getInt("depositRegisterImageCount", 8);
        if (imageCount > 0 && imageCount <= 30) {
            return imageCount;
        }
        return 8;
    }

    public static void saveIsOnBoarding(Context context, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isOnBoarding", value).apply();
    }


    public static boolean getIsOnBoarding(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isOnBoarding", false);
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
