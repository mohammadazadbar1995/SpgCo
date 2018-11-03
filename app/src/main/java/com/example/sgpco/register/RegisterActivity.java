package com.example.sgpco.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.sgpco.R;
import com.example.sgpco.baseView.BaseTextView;
import com.example.sgpco.customView.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.edtName)
    CustomEditText edtName;
    @BindView(R.id.edtFamily)
    CustomEditText edtFamily;
    @BindView(R.id.edtPassword)
    CustomEditText edtPassword;
    @BindView(R.id.edtMobile)
    CustomEditText edtMobile;
    @BindView(R.id.btnRegister)
    BaseTextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background_login).into(image);
    }

    @OnClick(R.id.btnRegister)
    public void onViewClicked() {
    }
}
