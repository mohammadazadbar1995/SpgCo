package com.spg.sgpco.baseView;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.spg.sgpco.utils.Constants;

public class BaseToolbar extends Toolbar {
    public BaseToolbar(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setLayoutDirection(Constants.language.getLayoutDirection());
        }
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setLayoutDirection(Constants.language.getLayoutDirection());
        }

    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setLayoutDirection(Constants.language.getLayoutDirection());
        }
    }


}
