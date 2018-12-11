package com.spg.sgpco.utils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by m.Azadbar on 1/23/2018.
 */

public class ResendActiveCodeService extends Service {

    private final static String TAG = "BroadcastService";
    private boolean mRunning;
    public static final String COUNTDOWN_BR = "ir.delta.consultant";
    Intent bi = new Intent(COUNTDOWN_BR);

    CountDownTimer cdt = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Starting timer...");
        mRunning = true;
        PreferencesData.saveBoolean(getApplication(),"isServiceRun" , mRunning);
        cdt = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Log.i(TAG, "Countdown seconds remaining in service: " + millisUntilFinished / 1000);
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {

                Log.i(TAG, "Timer finished");
                mRunning=false;
                PreferencesData.saveBoolean(getApplication(),"isServiceRun" , mRunning);
                stopSelf();
            }
        };

        cdt.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }
}
