package com.spg.sgpco.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.spg.sgpco.R;
import com.spg.sgpco.adapter.ShowInfoAdapter;
import com.spg.sgpco.baseView.BaseLinearLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class InfoListDialog extends Dialog {


    public ArrayList<String> errorMsg;
    public String title;
    @BindView(R.id.tvShow)
    BaseTextView tvShow;
    @BindView(R.id.rvShowError)
    RecyclerView rvShowError;
    @BindView(R.id.llError)
    BaseLinearLayout llError;
    @BindView(R.id.rlError)
    BaseLinearLayout rlError;
    @BindView(R.id.btnOk)
    BaseTextView btnOk;
    @BindView(R.id.rlBtn)
    BaseLinearLayout rlBtn;


    public InfoListDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(getContext(), R.layout.info_list_dialog, null);
        setContentView(view);

        ButterKnife.bind(this, view);
        if (getWindow() != null) {
            Window window = getWindow();
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Point size = Constants.getScreenSize(windowManager);
                int width = (int) Math.min(size.x * 0.90, getContext().getResources().getDimensionPixelSize(R.dimen.max_dialog_width));
                int maxHeight = getContext().getResources().getDimensionPixelSize(R.dimen.max_dialog_show_info);
                int cellHeight = getContext().getResources().getDimensionPixelSize(R.dimen.defult_height_dilog_info);
                int heightDialog = (cellHeight) * (4 + errorMsg.size());
                if (heightDialog > maxHeight) {
                    heightDialog = maxHeight;
                } else if (heightDialog <= (cellHeight * 4)) {
                    heightDialog = cellHeight * 5;
                }
                window.setLayout(width, heightDialog);
                window.setGravity(Gravity.CENTER);
            }

        }

        btnOk.setText(getContext().getString(R.string.ok));
        tvShow.setText(title);
        setAdapter();

    }


    private void setAdapter() {

        ShowInfoAdapter adapter = new ShowInfoAdapter(errorMsg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvShowError.setLayoutManager(layoutManager);
        rvShowError.setAdapter(adapter);

    }


    @OnClick({R.id.btnOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                dismiss();
                break;

        }
    }


}
