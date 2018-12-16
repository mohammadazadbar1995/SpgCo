package com.spg.sgpco.forgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.activity.HomeActivity;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.CustomEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.register.VerifyCodeActivity;
import com.spg.sgpco.service.Request.ResetPasswordService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.RequestModel.ResetPasswordReq;
import com.spg.sgpco.service.ResponseModel.ResetPasswordResponse;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {


    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.logo)
    BaseImageView logo;
    @BindView(R.id.txt)
    BaseTextView txt;
    @BindView(R.id.edtPassword)
    CustomEditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    CustomEditText edtConfirmPassword;
    @BindView(R.id.btnLogin)
    BaseTextView btnLogin;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background).into(image);
    }


    private void resetPassword() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        ResetPasswordReq req = new ResetPasswordReq();
        req.setPassword(Constants.convertToEnglishDigits(edtPassword.getValueString()));
        ResetPasswordService.getInstance().resetPassword(getResources(), req, new ResponseListener<ResetPasswordResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, 0);
            }

            @Override
            public void onSuccess(ResetPasswordResponse response) {

                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    Toast.makeText(ResetPasswordActivity.this, response.getResult(), Toast.LENGTH_LONG).show();
                    Intent enterCode = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                    startActivity(enterCode);
                    finish();

                }
            }

            @Override
            public void onUtorized() {

                finish();
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                PreferencesData.isLogin(ResetPasswordActivity.this, false);

                startActivity(intent);
            }
        });
    }


    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        if (isValidData()) {
            resetPassword();
        }

    }

    private boolean isValidData() {

        ArrayList<String> errorMsgList = new ArrayList<>();

        if (edtPassword.getError() != null) {
            errorMsgList.add(edtPassword.getError());
        }

        if (edtConfirmPassword.getError() != null) {
            errorMsgList.add(edtConfirmPassword.getError());
        }

        if (!edtPassword.getValueString().equals(edtConfirmPassword.getValueString())) {
            String message = getResources().getString(R.string.not_equal);
            errorMsgList.add(message);
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
            resetPassword();
        });
        customDialog.setCancelListener(getString(R.string.cancel), view -> customDialog.dismiss());
        customDialog.setIcon(R.drawable.ic_error);
        if (description != null) {
            customDialog.setDescription(description);
        }

        customDialog.setDialogTitle(getString(R.string.communicationError));
        customDialog.show();

    }

}
