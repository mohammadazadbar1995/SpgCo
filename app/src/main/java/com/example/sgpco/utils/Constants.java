package com.example.sgpco.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.example.sgpco.R;
import com.example.sgpco.enums.DirectionEnum;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


import static java.lang.Math.log10;

public class Constants {


    public static Language language = new Language("فارسی", "fa", DirectionEnum.RTL);

    //
    public static String URL_API = "https://api.delta.ir/api/";
    //    public static String URL_API = "https://172.16.1.123/api/";
    public static String URL_MAG_API = "https://mag.delta.ir/wp-json/wp/v2/";
    public static String Download_Base_Url = "https://delta.ir/";



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
