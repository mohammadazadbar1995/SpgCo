package com.spg.sgpco.baseView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.controller.AppController;
import com.spg.sgpco.dialog.InfoListDialog;
import com.spg.sgpco.utils.Constants;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {

    public int currentCityId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        try {
            Configuration configuration = getResources().getConfiguration();
            configuration.locale = Constants.language.getLocale();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(configuration.locale);
            }
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        } catch (Exception ignored) {
        }
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        AppController.setActivityContext(this,this);
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        newConfig.locale = Constants.language.getLocale();
        getApplicationContext().getResources().updateConfiguration(newConfig, null);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fade_out);
    }

//    public void checkArrowRtlORLtr(View view) {
//        if (Constants.language.getDirection() == DirectionEnum.LTR) {
//            view.setBackgroundResource(R.drawable.ic_back_english);
//        } else {
//            view.setBackgroundResource(R.drawable.ic_back);
//        }
//    }

    public void showInfoDialog(String title, ArrayList<String> errorMsgList) {
        InfoListDialog infoListDialog = new InfoListDialog(this);
        infoListDialog.errorMsg = errorMsgList;
        infoListDialog.title = title;
        infoListDialog.show();
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }


}
