package com.spg.sgpco.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.activity.BackPressedFragment;
import com.spg.sgpco.activity.HomeActivity;
import com.spg.sgpco.activity.MainActivity;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.GetInfoService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.Request.UpdateProfileService;
import com.spg.sgpco.service.RequestModel.UpdateProfileReq;
import com.spg.sgpco.service.ResponseModel.GetInfoResponse;
import com.spg.sgpco.service.ResponseModel.UpdateProfileResponse;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class UpdateProfileFragment extends BaseFragment implements BackPressedFragment {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tvMobile)
    BaseTextView tvMobile;
    @BindView(R.id.edtName)
    CustomEditText edtName;
    @BindView(R.id.edtFamily)
    CustomEditText edtFamily;
    @BindView(R.id.btnUpdateProfile)
    BaseTextView btnUpdateProfile;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    @BindView(R.id.tvName)
    BaseTextView tvName;


    public UpdateProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getString(R.string.edit_profile));


        getInfoRequest();
        return view;

    }

    private void getInfoRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        GetInfoService.getInstance().getInfo(getResources(), new ResponseListener<GetInfoResponse>() {
            @Override
            public void onGetErrore(String error) {
                if (tvMobile == null){
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(GetInfoResponse response) {
                if (tvMobile == null){
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getReuslt() != null) {
                    tvMobile.setText(response.getReuslt().getUser_login());
                    tvName.setText(response.getReuslt().getDisplay_name());
                    PreferencesData.saveString(getActivity(), "name", response.getReuslt().getDisplay_name());
                    PreferencesData.saveString(getActivity(), "mobile", response.getReuslt().getUser_login());

                }
            }

            @Override
            public void onUtorized() {
                if (getActivity() == null){
                    return;
                }
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                PreferencesData.isLogin(getActivity(), false);

                startActivity(intent);
            }
        });
    }

    public void showErrorDialog(String description) {

        if (getActivity() == null) {
            return;
        }
        CustomDialog customDialog = new CustomDialog(getActivity());
        customDialog.setOkListener(getString(R.string.retry_text), view -> {
            customDialog.dismiss();
            roundedLoadingView.setVisibility(View.VISIBLE);
            getInfoRequest();
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


    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();

        }
    }

    @OnClick(R.id.btnUpdateProfile)
    public void onViewClicked() {
        if (isValidData())
            updateProfileRequest();
    }


    private boolean isValidData() {
        ArrayList<String> errorMsgList = new ArrayList<>();

        if (edtName.getError() != null) {
            errorMsgList.add(edtName.getError());
        }

        if (edtFamily.getError() != null) {
            errorMsgList.add(edtFamily.getError());
        }

        if (errorMsgList.size() > 0) {
            showInfoDialog(getString(R.string.fill_following), errorMsgList);
            return false;
        }
        return true;
    }

    private void updateProfileRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        UpdateProfileReq req = new UpdateProfileReq();
        req.setDisplay_name(edtName.getValueString() + " " + edtFamily.getValueString());
        UpdateProfileService.getInstance().updateProfile(getResources(), req, new ResponseListener<UpdateProfileResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(UpdateProfileResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getResult() != null) {
                    Toast.makeText(getActivity(), response.getResult(), Toast.LENGTH_SHORT).show();
                    getInfoRequest();
                    edtName.setBody("");
                    edtFamily.setBody("");

                }
            }

            @Override
            public void onUtorized() {
                if (getActivity() == null){
                    return;
                }
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                PreferencesData.isLogin(getActivity(), false);

                startActivity(intent);
            }
        });
    }
}
