package com.spg.sgpco.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.utils.Constants;

import butterknife.BindView;

public class LoadingView extends RelativeLayout {

    @BindView(R.id.tvTitle)
    BaseTextView tvTitle;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.btnOk)
    BaseTextView btnOk;
    @BindView(R.id.root)
    BaseRelativeLayout root;


    public void setButtonClickListener(OnClickListener onClickListener) {
        btnOk.setOnClickListener(onClickListener);
    }

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.custom_loading_view, this, true);
        tvTitle = findViewById(R.id.tvTitle);
        progress = findViewById(R.id.progress);
        btnOk = findViewById(R.id.btnOk);
        root = findViewById(R.id.root);
        this.setLayoutDirection(Constants.language.getLayoutDirection());
    }


    public void showLoading() {
        this.setVisibility(View.VISIBLE);
        btnOk.setVisibility(GONE);
        root.setVisibility(VISIBLE);
        tvTitle.setVisibility(VISIBLE);
        progress.setVisibility(VISIBLE);
        tvTitle.setText(getResources().getString(R.string.receiving_information));
    }

    public void stopLoading() {
        this.setVisibility(GONE);
        progress.setVisibility(GONE);
    }

    public void showError(String error) {
        this.setVisibility(View.VISIBLE);
        if (error != null) {
            tvTitle.setText(error);
        } else {
            tvTitle.setText(getResources().getString(R.string.communicationError));
        }
        btnOk.setVisibility(VISIBLE);
        progress.setVisibility(GONE);
    }
}
