package com.spg.sgpco.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.activity.MainActivity;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.CustomEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.register.RegisterActivity;
import com.spg.sgpco.service.Request.LoginService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.RequestModel.LoginReq;
import com.spg.sgpco.service.ResponseModel.LoginResponse;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


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
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private LoginReq req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background_login).into(image);
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnLogin:
                if (isValidData()) {
                    loginRequest();
                }
                finish();
                break;
            case R.id.btnRegister:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loginRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        req = new LoginReq();
        req.setPhonenumber(edtMobile.getValueString());
        req.setPassword(edtPassword.getValueString());
        LoginService.getInstance().login(getResources(), req, new ResponseListener<LoginResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, 0);
            }

            @Override
            public void onSuccess(LoginResponse response) {

                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    PreferencesData.saveToken(LoginActivity.this, response.getResult().getToken());
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.putExtra("login", response.getResult());
                    startActivity(loginIntent);

                }
            }
        });
    }

    private boolean isValidData() {

        ArrayList<String> errorMsgList = new ArrayList<>();

        if (edtMobile.getError() != null) {
            errorMsgList.add(edtMobile.getError());
        }

        if (edtPassword.getError() != null) {
            errorMsgList.add(edtPassword.getError());
        }

        if (errorMsgList.size() > 0) {
            showInfoDialog(getString(R.string.fill_following), errorMsgList);
            return false;
        }

        return true;
    }

    public void showErrorDialog(String description, int type) {

        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setOkListener(getString(R.string.retry_text), view -> {
            customDialog.dismiss();
            roundedLoadingView.setVisibility(View.VISIBLE);
            loginRequest();
        });
        customDialog.setCancelListener(getString(R.string.cancel), view -> customDialog.dismiss());
        customDialog.setIcon(R.drawable.ic_error);
        if (description != null) {
            customDialog.setDescription(description);
        }

        customDialog.setDialogTitle(getString(R.string.communicationError));
        customDialog.show();

    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }


}
