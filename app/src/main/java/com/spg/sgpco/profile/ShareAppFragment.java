package com.spg.sgpco.profile;

import android.graphics.PorterDuff;
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
import com.spg.sgpco.baseView.BaseTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class ShareAppFragment extends Fragment implements BackPressedFragment {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.logo)
    BaseImageView logo;
    @BindView(R.id.btnShare)
    BaseTextView btnShare;


    public ShareAppFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share_app, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getString(R.string.share));
        logo.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btnShare)
    public void onViewClicked() {
        Toast.makeText(getActivity(), "send this app", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
