package com.spg.sgpco.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.service.ResponseModel.ProjectListResultItem;
import com.spg.sgpco.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by m.Azadbar on 10/7/2017.
 */

public class SelectedDialog extends Dialog {


    @BindView(R.id.profile_image)
    BaseImageView profileImage;
    @BindView(R.id.tvRealtorName)
    BaseTextView tvRealtorName;
    @BindView(R.id.btnObservation)
    BaseTextView btnObservation;
    @BindView(R.id.btnEdit)
    BaseTextView btnEdit;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.btnCancel)
    BaseTextView btnCancel;
    @BindView(R.id.btnDelete)
    BaseTextView btnDelete;
    private ProjectListResultItem item;
    private OpenEditFragment listener;

    public void setListener(OpenEditFragment listener) {
        this.listener = listener;
    }

    public ProjectListResultItem getItem() {
        return item;
    }

    public void setItem(ProjectListResultItem item) {
        this.item = item;
    }

    public SelectedDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(getContext(), R.layout.selected_dialog, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(false);

        Glide.with(getContext()).load(R.drawable.logo).into(profileImage);

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                Point size = Constants.getScreenSize(windowManager);
                double width = size.x * 0.7;
                if (width >= getContext().getResources().getDimensionPixelSize(R.dimen.dialog_width_comminucatio)) {
                    width = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_width_comminucatio);
                } else {
                    width = size.x * 0.7;
                }
                window.setLayout((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
    }


    @OnClick({R.id.btnObservation, R.id.btnEdit, R.id.btnCancel, R.id.btnDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnObservation:
                dismiss();
                listener.onPdfItem();
//                getPdfItemRequest();
                break;
            case R.id.btnEdit:
                dismiss();
                listener.onEdit();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnDelete:
                dismiss();
                listener.onDelete();
                break;
        }
    }


//    private void getPdfItemRequest() {
//
//        ShowProjectWebViewFragment showProjectWebViewFragment = new ShowProjectWebViewFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("link", item.getLink());
//        showProjectWebViewFragment.setArguments(bundle);
//        FragmentManager fragMgr = getContext().getSupportFragmentManager();
//        FragmentTransaction fragTrans = fragMgr.beginTransaction();
//        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//        fragTrans.add(R.id.frameLayout, showProjectWebViewFragment, ShowProjectWebViewFragment.class.getName());
//        fragTrans.addToBackStack(ShowProjectWebViewFragment.class.getName());
//        fragTrans.commit();
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse(Constants.OpenProjectUrl + item.getLink()));
//        startActivity(browserIntent);
//    }


}
