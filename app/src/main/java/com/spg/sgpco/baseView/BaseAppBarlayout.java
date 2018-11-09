package com.spg.sgpco.baseView;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

import com.spg.sgpco.utils.Constants;

public class BaseAppBarlayout extends AppBarLayout {

    public BaseAppBarlayout(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setLayoutDirection(Constants.language.getLayoutDirection());
        }
    }

    public BaseAppBarlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setLayoutDirection(Constants.language.getLayoutDirection());
        }
    }
}


