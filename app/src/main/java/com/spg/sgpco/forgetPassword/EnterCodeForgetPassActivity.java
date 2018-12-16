package com.spg.sgpco.forgetPassword;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
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
import com.spg.sgpco.service.Request.EnterCodePasswordService;
import com.spg.sgpco.service.Request.ForgetPasswordService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.RequestModel.ForgetPassReq;
import com.spg.sgpco.service.RequestModel.LoginWithCodeForgetPassReq;
import com.spg.sgpco.service.ResponseModel.EnterCodeResponse;
import com.spg.sgpco.service.ResponseModel.VerifyResponse;
import com.spg.sgpco.utils.PreferencesData;
import com.spg.sgpco.utils.ResendActiveCodeService;

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
    @BindView(R.id.btnRetyCodeVerification)
    BaseTextView btnRetyCodeVerification;
    @BindView(R.id.tvCounter)
    BaseTextView tvCounter;
    private LoginWithCodeForgetPassReq req;
    private ForgetPassReq forgetPassReq;
    private boolean inCountDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code_forget_pass);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        forgetPassReq = intent.getParcelableExtra("phoneNumber");
        Glide.with(this).load(R.drawable.background).into(image);
        countDownService();
    }


    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields

        }
    };

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.i("", "Countdown seconds remaining in GUI: " + millisUntilFinished / 1000);
            tvCounter.setText(millisUntilFinished / 1000 + "");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (tvCounter.getText().toString().equals("1")) {
                        tvCounter.setText("0");
                        tvCounter.setTextColor(Color.parseColor("#E93A36"));
                        inCountDown = true;
                        btnRetyCodeVerification.setEnabled(true);
                        btnRetyCodeVerification.setClickable(true);

                    }
                }
            }, 1000);

        }
    }

    private void countDownService() {
        this.startService(new Intent(this, ResendActiveCodeService.class));
        Log.i("", "Started service");
        this.registerReceiver(br, new IntentFilter(ResendActiveCodeService.COUNTDOWN_BR));

    }

    private void enterCodePasss() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        req = new LoginWithCodeForgetPassReq();
        req.setPhonenumber(forgetPassReq.getPhonenumber());
        req.setCode(edtEnterCode.getValueString());
        EnterCodePasswordService.getInstance().enterCodePassword(getResources(), req, new ResponseListener<EnterCodeResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, 0);
            }

            @Override
            public void onSuccess(EnterCodeResponse response) {

                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    PreferencesData.saveToken(EnterCodeForgetPassActivity.this, response.getResult().getToken());
                    Intent enterCode = new Intent(EnterCodeForgetPassActivity.this, ResetPasswordActivity.class);
                    Toast.makeText(EnterCodeForgetPassActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    PreferencesData.saveToken(EnterCodeForgetPassActivity.this, response.getResult().getToken());
                    PreferencesData.saveString(EnterCodeForgetPassActivity.this, "name", response.getResult().getUser_display_name());
                    PreferencesData.saveString(EnterCodeForgetPassActivity.this, "mobile", response.getResult().getUser_nicename());
                    startActivity(enterCode);
                    finish();
                }
            }

            @Override
            public void onUtorized() {
                finish();
                Intent intent = new Intent(EnterCodeForgetPassActivity.this, LoginActivity.class);
                PreferencesData.isLogin(EnterCodeForgetPassActivity.this, false);

                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.btnLogin, R.id.btnRetyCodeVerification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (isValidData()) {
                    enterCodePasss();
                }
                break;
            case R.id.btnRetyCodeVerification:
                if (inCountDown) {
                    countDownService();
                    retryCodeAgain();
                    inCountDown = false;
                } else {
                    Toast.makeText(this, "لطفا شکیبا باشید", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    private void retryCodeAgain() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        ForgetPassReq req = new ForgetPassReq();
        req.setPhonenumber(forgetPassReq.getPhonenumber());
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
                    Toast.makeText(EnterCodeForgetPassActivity.this, response.getResult(), Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onUtorized() {
                finish();
                Intent intent = new Intent(EnterCodeForgetPassActivity.this, LoginActivity.class);
                PreferencesData.isLogin(EnterCodeForgetPassActivity.this, false);
                startActivity(intent);
            }
        });
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
