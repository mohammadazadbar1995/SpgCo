package com.spg.sgpco.forgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

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
import com.spg.sgpco.service.Request.EnterCodePasswordService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.RequestModel.ForgetPassReq;
import com.spg.sgpco.service.RequestModel.LoginWithCodeForgetPassReq;
import com.spg.sgpco.service.ResponseModel.LoginResponse;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnterCodeForgetPassActivity extends BaseActivity {


    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.edtEnterCode)
    CustomEditText edtEnterCode;
    @BindView(R.id.btnLogin)
    BaseTextView btnLogin;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private LoginWithCodeForgetPassReq req;
    private ForgetPassReq forgetPassReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code_forget_pass);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        forgetPassReq = intent.getParcelableExtra("phoneNumber");
        Glide.with(this).load(R.drawable.background).into(image);
    }


    private void enterCodePasss() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        req = new LoginWithCodeForgetPassReq();
        req.setPhonenumber(forgetPassReq.getPhonenumber());
        req.setCode(edtEnterCode.getValueString());
        EnterCodePasswordService.getInstance().enterCodePassword(getResources(), req, new ResponseListener<LoginResponse>() {
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
                    PreferencesData.saveToken(EnterCodeForgetPassActivity.this, response.getResult().getToken());
                    Intent enterCode = new Intent(EnterCodeForgetPassActivity.this, MainActivitySecond.class);
                    enterCode.putExtra("login", response.getResult());
                    startActivity(enterCode);
                    finish();

                }
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        if (isValidData()) {
            enterCodePasss();
        }

    }
    private boolean isValidData() {

        ArrayList<String> errorMsgList = new ArrayList<>();

        if (edtEnterCode.getError() != null) {
            errorMsgList.add(edtEnterCode.getError());
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
            enterCodePasss();
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
