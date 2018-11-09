package com.spg.sgpco.controller;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.thefinestartist.Base;

public class AppController extends Application {

    private static Activity CurrentActivity = null;
    private static Context CurrentContext;


    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());//TODO Enable fabric
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/iransansmobile.ttfontAttrId(R.attr.fontPath).build()
//        );

        Base.initialize(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public static void setActivityContext(Activity activity, Context context) {
        CurrentActivity = activity;
        CurrentContext = context;
    }

    public static Activity getCurrentActivity() {
        return CurrentActivity;
    }

    public static Context getCurrentContext() {
        return CurrentContext;
    }

}
