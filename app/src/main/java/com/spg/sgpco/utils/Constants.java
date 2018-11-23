package com.spg.sgpco.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.spg.sgpco.enums.DirectionEnum;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Constants {


    public static final int TYPE_PROJECT_CODE = 100;
    public static final int STATE_CODE = 101;
    public static final int ADD_CUSTOMER = 104;
    public static final int HEAT_SOURCE = 105;
    public static final int CITY_CODE = 106;
    public static final int TYPE_OF_CONTROL_SYSTEMS = 107;
    public static final int GENDER_FLOOR = 108;
    public static final int TYPE_SPACE = 109;
    public static Language language = new Language("فارسی", "fa", DirectionEnum.RTL);

    //
    public static String URL_API = "http://sgpmojri.ir/a/api/";



    public static Point getScreenSize(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new Point(width, height);
    }


    public static boolean isPackageInstalled(PackageManager packageManager) {
        try {
            packageManager.getPackageInfo("com.farsitel.bazaar", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        } catch (SecurityException e) {
            return true;
        }
    }

    public static String convertNumberToDecimal(double d) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###.###");
        return formatter.format(d);
    }


    public static void shareTextUrl(Context context, String text) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(share, "Share link!"));
    }

//    public static void setBackgroundProgress(Context context, ProgressBar progressBar) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
//            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(context, R.color.greenColor));
//            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));
//
//        } else {
//            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context
//                    , R.color.greenColor), PorterDuff.Mode.SRC_IN);
//        }
//    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

//    public static String priceConvertor(long number, Resources res) {
//
//        double num = number / 10.0;
//        if (num <= 0) {
//            return "";
//        }
//        int digitCount = (int) Math.ceil(log10(num));
//
//        if (digitCount <= 6) {
//            return Constants.convertNumberToDecimal(num);
//        } else if (digitCount < 10) {
//            double result = num / Math.pow(10, 6);
//            return Constants.convertNumberToDecimal(result) + " " + res.getString(R.string.million) + " " + res.getString(R.string.toman);
//        }
//
//        double result = num / Math.pow(10, 9);
//        return Constants.convertNumberToDecimal(result) + " " + res.getString(R.string.billion) + " " + res.getString(R.string.toman);
//
//    }

}
