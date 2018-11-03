package com.example.sgpco.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.sgpco.R;
import com.example.sgpco.baseView.BaseTextView;
import com.example.sgpco.customView.CustomEditText;
import com.example.sgpco.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.edtMobile)
    CustomEditText edtMobile;
    @BindView(R.id.edtPassword)
    CustomEditText edtPassword;
    @BindView(R.id.btnLogin)
    BaseTextView btnLogin;
    @BindView(R.id.btnRegister)
    BaseTextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background_login).into(image);
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                break;
            case R.id.btnRegister:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }


}
