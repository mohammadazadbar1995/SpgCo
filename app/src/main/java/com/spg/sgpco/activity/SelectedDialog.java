package com.spg.sgpco.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.service.Request.PdfItemService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.GalleryItemResponse;
import com.spg.sgpco.service.ResponseModel.ProjectListResultItem;
import com.spg.sgpco.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by m.Azadbar on 10/7/2017.
 */

public class SelectedDialog extends Dialog {


    private final Context context;
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
        this.context = context;
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
        profileImage.setColorFilter(getContext().getResources().getColor(R.color.redColor), PorterDuff.Mode.SRC_ATOP);

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


    @OnClick({R.id.btnObservation, R.id.btnEdit, R.id.btnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnObservation:
                getPdfItemRequest();
                break;
            case R.id.btnEdit:
                dismiss();
                listener.onEdit();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }


    private void getPdfItemRequest() {
        PdfItemService.getInstance().pdfItem(getContext().getResources(), item.getId(), new ResponseListener<GalleryItemResponse>() {
            @Override
            public void onGetErrore(String error) {

            }

            @Override
            public void onSuccess(GalleryItemResponse response) {

            }
        });
    }


}
