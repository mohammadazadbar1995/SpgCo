package com.spg.sgpco.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.activity.MainActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background_login).into(image);
        new Handler().postDelayed(() -> {
            if (PreferencesData.getIsLogin(SplashActivity.this)) {
                Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
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


