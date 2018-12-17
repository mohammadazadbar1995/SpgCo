package com.spg.sgpco.customView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.utils.Constants;

import butterknife.BindView;

public class RoundedLoadingView extends RelativeLayout {

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.root)
    BaseRelativeLayout root;

    public RoundedLoadingView(Context context) {
        this(context, null);
    }

    public RoundedLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_rounded_loading_view, this, true);
        progress = findViewById(R.id.progress);
        root = findViewById(R.id.root);
        this.setLayoutDirection(Constants.language.getLayoutDirection());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawableProgress = DrawableCompat.wrap(progress.getIndeterminateDrawable());
            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(context, R.color.colorAccent));
            progress.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

        } else {
            progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }


    public void showLoading() {
        root.setVisibility(VISIBLE);
        progress.setVisibility(VISIBLE);

    }

    public void showError(String error) {
        progress.setVisibility(GONE);
    }
}
