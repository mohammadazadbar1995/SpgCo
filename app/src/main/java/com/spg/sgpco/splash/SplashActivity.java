package com.spg.sgpco.splash;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.activity.MainActivity;
import com.spg.sgpco.activity.MainActivitySecond;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.utils.PreferencesData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.root)
    BaseRelativeLayout root;

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @BindView(R.id.logo)
    AppCompatImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background).into(image);
        Glide.with(this).load(R.drawable.logo).into(logo);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawableProgress = DrawableCompat.wrap(progress.getIndeterminateDrawable());
            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(this, R.color.white));
            progress.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

        } else {
            progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        }
        new Handler().postDelayed(() -> {
            if (PreferencesData.getIsLogin(SplashActivity.this)) {
                Intent mainActivity = new Intent(SplashActivity.this, MainActivitySecond.class);
                SplashActivity.this.startActivity(mainActivity);
                SplashActivity.this.finish();
            } else {
                Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(loginActivity);
                SplashActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGTH);
    }
}


