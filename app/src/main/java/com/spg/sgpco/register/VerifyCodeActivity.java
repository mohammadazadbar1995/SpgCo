package com.spg.sgpco.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.activity.MainActivity;
import com.spg.sgpco.activity.MainActivitySecond;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.CustomEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.RegisterService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.Request.VerifyCodeService;
import com.spg.sgpco.service.RequestModel.RegisterReq;
import com.spg.sgpco.service.ResponseModel.LoginResponse;
import com.spg.sgpco.service.ResponseModel.VerifyResponse;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyCodeActivity extends BaseActivity {


    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.edtCodeVerify)
    CustomEditText edtCodeVerify;
    @BindView(R.id.btnVerify)
    BaseTextView btnVerify;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.background).into(image);
    }

    @OnClick(R.id.btnVerify)
    public void onViewClicked() {
        if (isValidData()) {
            sendRegisterInfo();
        }
    }


    private void sendRegisterInfo() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);
        RegisterReq req = new RegisterReq();
        req.setCode(edtCodeVerify.getValueString());
        req.setForename(PreferencesData.getString(this, "foreName"));
        req.setSurename(PreferencesData.getString(this, "sureName"));
        req.setPassword(PreferencesData.getString(this, "password"));
        req.setPhonenumber(PreferencesData.getString(this, "mobile"));

        RegisterService.getInstance().regiter(getResources(), req, new ResponseListener<LoginResponse>() {
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
                    PreferencesData.saveToken(VerifyCodeActivity.this, response.getResult().getToken());
                    Intent loginIntent = new Intent(VerifyCodeActivity.this, MainActivitySecond.class);
                    loginIntent.putExtra("login", response.getResult());
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }

    private boolean isValidData() {

        ArrayList<String> errorMsgList = new ArrayList<>();

        if (edtCodeVerify.getError() != null) {
            errorMsgList.add(edtCodeVerify.getError());
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
            sendRegisterInfo();
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
