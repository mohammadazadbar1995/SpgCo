package com.spg.sgpco.forgetPassword;

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
import com.spg.sgpco.register.VerifyCodeActivity;
import com.spg.sgpco.service.Request.ForgetPasswordService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.RequestModel.ForgetPassReq;
import com.spg.sgpco.service.ResponseModel.VerifyResponse;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {


    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.edtMobile)
    CustomEditText edtMobile;
    @BindView(R.id.btnSendCode)
    BaseTextView btnSendCode;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private ForgetPassReq req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background).into(image);
    }


    private void forgetPassword() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        req = new ForgetPassReq();
        req.setPhonenumber(edtMobile.getValueString());
        ForgetPasswordService.getInstance().forgetPassword(getResources(), req, new ResponseListener<VerifyResponse>() {
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
                    Toast.makeText(ForgetPasswordActivity.this, response.getResult(), Toast.LENGTH_LONG).show();
                    Intent enterCode = new Intent(ForgetPasswordActivity.this, EnterCodeForgetPassActivity.class);
                    enterCode.putExtra("phoneNumber", req);
                    startActivity(enterCode);
                    finish();

                }
            }

            @Override
            public void onUtorized() {
                finish();
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                PreferencesData.isLogin(ForgetPasswordActivity.this, false);

                startActivity(intent);
            }
        });
    }



    @OnClick(R.id.btnSendCode)
    public void onViewClicked() {
        if (isValidData()) {
            forgetPassword();
        }

    }

    private boolean isValidData() {

        ArrayList<String> errorMsgList = new ArrayList<>();

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
            forgetPassword();
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
