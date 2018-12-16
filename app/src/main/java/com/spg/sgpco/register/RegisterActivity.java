package com.spg.sgpco.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.activity.MainActivity;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.CustomEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.Request.VerifyCodeService;
import com.spg.sgpco.service.RequestModel.VerifyReq;
import com.spg.sgpco.service.ResponseModel.VerifyResponse;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

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
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background).into(image);
    }

    @OnClick(R.id.btnRegister)
    public void onViewClicked() {
        if (isValidData()) {

            verifyCodeRequest();
        }
    }

    private void verifyCodeRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);
        VerifyReq req = new VerifyReq();
        req.setForename(edtName.getValueString());
        PreferencesData.saveString(this, "foreName", edtName.getValueString());
        req.setSurename(edtFamily.getValueString());
        PreferencesData.saveString(this, "sureName", edtFamily.getValueString());
        req.setPassword(Constants.convertToEnglishDigits(edtPassword.getValueString()));
        PreferencesData.saveString(this, "password", Constants.convertToEnglishDigits(edtPassword.getValueString()));
        req.setPhonenumber(edtMobile.getValueString());
        PreferencesData.saveString(this, "mobile", edtMobile.getValueString());
        VerifyCodeService.getInstance().verifyCode(getResources(), req, new ResponseListener<VerifyResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, 0);
            }

            @Override
            public void onSuccess(VerifyResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    Intent intent = new Intent(RegisterActivity.this, VerifyCodeActivity.class);
                    Toast.makeText(RegisterActivity.this, response.getResult(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onUtorized() {
                finish();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                PreferencesData.isLogin(RegisterActivity.this, false);

                startActivity(intent);
            }
        });

    }

    private boolean isValidData() {

        ArrayList<String> errorMsgList = new ArrayList<>();

        if (edtName.getError() != null) {
            errorMsgList.add(edtName.getError());
        }

        if (edtFamily.getError() != null) {
            errorMsgList.add(edtFamily.getError());
        }

        if (edtPassword.getError() != null) {
            errorMsgList.add(edtPassword.getError());
        }

        if (edtMobile.getError() != null) {
            errorMsgList.add(edtMobile.getError());
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
            verifyCodeRequest();
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
