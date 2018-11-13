package com.spg.sgpco.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseLinearLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by m.azadbar on 10/7/2017.
 */

public class CustomDialog extends Dialog {

    @BindView(R.id.icon)
    AppCompatImageView icon;
    @BindView(R.id.tvTitle)
    BaseTextView tvTitle;
    @BindView(R.id.tvDescriptioError)
    BaseTextView tvDescriptioError;
    @BindView(R.id.btnCancel)
    BaseTextView btnCancel;
    @BindView(R.id.btnOk)
    BaseTextView btnOk;
    @BindView(R.id.rlBtn)
    BaseLinearLayout rlBtn;

    private String okTitle;
    private View.OnClickListener cancelListener;
    private View.OnClickListener okListener;
    private String cancelTitle;
    private String description;
    private String title;
    private int image;
    private int color;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(getContext(), R.layout.custom_dialog, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(false);
        if (cancelListener != null) {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(cancelTitle);
            btnCancel.setOnClickListener(cancelListener);
        } else {
            btnCancel.setVisibility(View.GONE);
        }
        if (okTitle != null) {
            btnOk.setText(okTitle);
            btnOk.setVisibility(View.VISIBLE);
            btnOk.setOnClickListener(okListener);
        } else {
            btnOk.setVisibility(View.GONE);
        }
        tvTitle.setText(title);
        tvDescriptioError.setText(description);

        if (image > 0) {
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(image);
        }
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                Point size = Constants.getScreenSize(windowManager);
                int width = (int) Math.min(size.x * 0.9, getContext().getResources().getDimensionPixelSize(R.dimen.max_dialog_width));
                window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    public void setIcon(int image) {
        this.image = image;
    }

    public void setCancelListener(String cancelTitle, View.OnClickListener cancelListener) {
        this.cancelTitle = cancelTitle;
        this.cancelListener = cancelListener;
    }

    public void setOkListener(String okTitle, View.OnClickListener okListener) {
        this.okTitle = okTitle;
        this.okListener = okListener;
    }

    public void setDialogTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

}
