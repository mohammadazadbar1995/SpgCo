package com.spg.sgpco.baseView;


import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.spg.sgpco.R;
import com.spg.sgpco.dialog.InfoListDialog;
import com.spg.sgpco.utils.Constants;

import java.util.ArrayList;

public class BaseFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        try {
            Configuration configuration = getResources().getConfiguration();
            configuration.locale = Constants.language.getLocale();
////            getApplicationContext().getResources().updateConfiguration(config, null);
//            getBaseContext().getResources().updateConfiguration(config,
//                    getBaseContext().getResources().getDisplayMetrics());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(configuration.locale);
            }
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        } catch (Exception ignored) {
        }
        if (getActivity() != null) {
            getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }

    }


    public void showInfoDialog(String title, ArrayList<String> errorMsgList) {
        if (getActivity() != null) {
            InfoListDialog infoListDialog = new InfoListDialog(getActivity());
            infoListDialog.errorMsg = errorMsgList;
            infoListDialog.title = title;
            infoListDialog.show();
        }
    }


    public boolean onBackPressed() {
        return false;
    }


    public void finishFragment(View view) {
        if (getActivity() != null) {
            view.setOnClickListener(view1 -> getActivity().finish());
        }

    }




}
