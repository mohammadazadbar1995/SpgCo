package com.spg.sgpco.profile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.activity.BackPressedFragment;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.RoundedLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class ContactUsFragment extends Fragment implements BackPressedFragment {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.logo)
    BaseImageView logo;
    @BindView(R.id.tvBrand)
    BaseTextView tvBrand;
    @BindView(R.id.tvTxt)
    BaseTextView tvTxt;
    @BindView(R.id.tvEmail)
    BaseTextView tvEmail;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    @BindView(R.id.tvPhoneOfficeTehran)
    BaseTextView tvPhoneOfficeTehran;
    @BindView(R.id.tvPhoneOfficeEsfahan)
    BaseTextView tvPhoneOfficeEsfahan;
    @BindView(R.id.tvPhoneOfficeMashhad)
    BaseTextView tvPhoneOfficeMashhad;


    public ContactUsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contect_us, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getString(R.string.contact_whit_us));
        logo.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        tvPhoneOfficeTehran.setText(getResources().getString(R.string.office_tehran) + ": " + "021-82377");
        tvPhoneOfficeEsfahan.setText(getResources().getString(R.string.office_esfahan) + ": " + "0311-69377");
        tvPhoneOfficeMashhad.setText(getResources().getString(R.string.office_mashhad) + ": " + "051-37530060");

        return view;

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

    @OnClick({ R.id.tvEmail, R.id.tvPhoneOfficeTehran, R.id.tvPhoneOfficeEsfahan, R.id.tvPhoneOfficeMashhad})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tvEmail:
                sendEmail();
                break;
            case R.id.tvPhoneOfficeTehran:
                callIntent("021-82377");
                break;
            case R.id.tvPhoneOfficeEsfahan:
                callIntent("0311-69377");
                break;
            case R.id.tvPhoneOfficeMashhad:
                callIntent("051-37530060");
                break;
        }
    }

    private void callIntent(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" +number));
        startActivity(intent);
    }

    private void sendEmail() {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.support_email_delta)});
            intent.putExtra(Intent.EXTRA_SUBJECT, "App feedback");
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email client installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
