package com.spg.sgpco.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.activity.BackPressedFragment;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.AboutSGPService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.AboutResponse;
import com.spg.sgpco.utils.PreferencesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class AboutSgpFragment extends BaseFragment implements BackPressedFragment {


    @BindView(R.id.addThermostatic)
    BaseImageView addThermostatic;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.share)
    BaseImageView share;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    Unbinder unbinder;
    @BindView(R.id.about)
    BaseTextView about;

    public AboutSgpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_sgp, container, false);
        unbinder = ButterKnife.bind(this, view);


        aboutSpgRequest();
        return view;

    }

    private void aboutSpgRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);
        AboutSGPService.getInstance().aboutSGP(getResources(), new ResponseListener<AboutResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(AboutResponse response) {
                enableDisableViewGroup(root, true);
                roundedLoadingView.setVisibility(View.GONE);
                if (getView() != null && getActivity() != null) {
                    if (response.isSuccess()) {
                        tvCenterTitle.setText(response.getResult().getTitle());
                        about.setText(response.getResult().getContent());
                    }
                }

            }

            @Override
            public void onUtorized() {
                if (getActivity() == null) {
                    return;
                }
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                PreferencesData.isLogin(getActivity(), false);
                startActivity(intent);
            }
        });

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

    public void showErrorDialog(String description) {

        if (getActivity() == null) {
            return;
        }
        CustomDialog customDialog = new CustomDialog(getActivity());
        customDialog.setOkListener(getString(R.string.retry_text), view -> {
            customDialog.dismiss();
            roundedLoadingView.setVisibility(View.VISIBLE);
            aboutSpgRequest();
        });
        customDialog.setCancelListener(getString(R.string.cancel), view -> customDialog.dismiss());
        customDialog.setIcon(R.drawable.ic_error);
        if (description != null) {
            customDialog.setDescription(description);
        }

        customDialog.setDialogTitle(getString(R.string.communicationError));
        customDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
