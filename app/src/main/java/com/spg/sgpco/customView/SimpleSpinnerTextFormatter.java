package com.spg.sgpco.customView;

import android.text.Spannable;
import android.text.SpannableString;

/**
 * Created by R.taghizadeh on 3/7/2018.
 */

public class SimpleSpinnerTextFormatter implements SpinnerTextFormatter{
    @Override
    public Spannable format(String text) {
        return new SpannableString(text);
    }
}
