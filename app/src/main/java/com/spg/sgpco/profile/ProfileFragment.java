package com.spg.sgpco.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomInsertDataView;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.LogoutService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.utils.PreferencesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class ProfileFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.editProfileLayout)
    CustomInsertDataView editProfileLayout;
    @BindView(R.id.contentLayout)
    CustomInsertDataView contentLayout;
    @BindView(R.id.galleryLayout)
    CustomInsertDataView galleryLayout;
    @BindView(R.id.introductionLayout)
    CustomInsertDataView introductionLayout;
    @BindView(R.id.contactlayout)
    CustomInsertDataView contactlayout;
    @BindView(R.id.exitLayout)
    CustomInsertDataView exitLayout;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    @BindView(R.id.tvName)
    BaseTextView tvName;


    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getString(R.string.setting));
        if (!TextUtils.isEmpty(PreferencesData.getString(getContext(), "name"))) {
            tvName.setText(PreferencesData.getString(getContext(), "name"));
        }


        editProfileLayout.setImgInfo(R.drawable.ic_person_gray);
        editProfileLayout.setTxtTitle("");
        editProfileLayout.setValue(getString(R.string.edit_profile));

        contentLayout.setImgInfo(R.drawable.ic_person_gray);
        contentLayout.setTxtTitle("");
        contentLayout.setValue(getString(R.string.content));


        galleryLayout.setImgInfo(R.drawable.ic_person_gray);
        galleryLayout.setTxtTitle("");
        galleryLayout.setValue(getString(R.string.gallery_photo));

        introductionLayout.setImgInfo(R.drawable.ic_person_gray);
        introductionLayout.setTxtTitle("");
        introductionLayout.setValue(getString(R.string.intro_friend));

        contactlayout.setImgInfo(R.drawable.ic_person_gray);
        contactlayout.setTxtTitle("");
        contactlayout.setValue(getString(R.string.contact_whit_us));

        exitLayout.setImgInfo(R.drawable.ic_person_gray);
        exitLayout.setTxtTitle("");
        exitLayout.setValue(getString(R.string.exit));

        Log.e("onCreate", "are");
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("onAttach", "omad");
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileName();
    }

    public void setProfileName() {
        if (!TextUtils.isEmpty(PreferencesData.getString(getContext(), "name"))) {
            tvName.setText(PreferencesData.getString(getContext(), "name"));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.editProfileLayout, R.id.contentLayout, R.id.galleryLayout, R.id.introductionLayout, R.id.contactlayout, R.id.exitLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editProfileLayout:
                updateProfile();
                break;
            case R.id.contentLayout:
                contentFragment();
                break;
            case R.id.galleryLayout:
                galleryFragment();
                break;
            case R.id.introductionLayout:
                introductionFragment();
                break;
            case R.id.contactlayout:
                contactWithUsFragment();
                break;
            case R.id.exitLayout:
                logoutDialog();
                break;
        }
    }

    private void galleryFragment() {
        if (getActivity() == null) {
            return;
        }

        loadFragment(new GalleryFragment(), GalleryFragment.class.getName(), false);

    }

    private void contentFragment() {
        if (getActivity() == null) {
            return;
        }

        loadFragment(new ShowContentFragment(), ShowContentFragment.class.getName(), false);

    }

    private void updateProfile() {
        if (getActivity() == null) {
            return;
        }
        loadFragment(new UpdateProfileFragment(), UpdateProfileFragment.class.getName(), false);
    }

    private void introductionFragment() {
        if (getActivity() == null) {
            return;
        }
        loadFragment(new ShareAppFragment(), ShareAppFragment.class.getName(), false);
    }

    private void contactWithUsFragment() {
        if (getActivity() == null) {
            return;
        }
        loadFragment(new ContactUsFragment(), ContactUsFragment.class.getName(), false);
    }


    private void logoutDialog() {
        if (getActivity() == null) {
            return;
        }
        CustomDialog customDialog = new CustomDialog(getActivity());
        customDialog.setOkListener(getString(R.string.dialog_yes), view -> {
            customDialog.dismiss();
            logoutRequest();
        });
        customDialog.setCancelListener(getString(R.string.dialog_no), view -> customDialog.dismiss());
        customDialog.setIcon(R.drawable.ic_error);

        customDialog.setDialogTitle(getString(R.string.sureـyouـwantـtoـleave));
        customDialog.show();
    }

    private void logoutRequest() {

        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);
        LogoutService.getInstance().logout(getResources(), new ResponseListener<LogoutService>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(LogoutService response) {
                if (getActivity() != null) {
                    enableDisableViewGroup(root, true);
                    roundedLoadingView.setVisibility(View.GONE);
                    PreferencesData.isLogin(getActivity(), false);
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                    getActivity().finish();
                }
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

    private void loadFragment(Fragment fragment, String fragmentTag, boolean hideOtherFragmnet) {
        if (getActivity() == null) {
            return;
        }
        FragmentManager fragMgr = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragTrans.add(R.id.frameLayout, fragment, fragmentTag);
        fragTrans.addToBackStack(fragmentTag);
        if (hideOtherFragmnet)
            hideOtherFragment(fragment);
        fragTrans.commit();

    }

    private void hideOtherFragment(Fragment currentFragment) {
        if (getActivity() == null) {
            return;
        }
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
            if (fragment != currentFragment)
                getActivity().getSupportFragmentManager().beginTransaction().hide(fragment).commit();
        }
    }

}
