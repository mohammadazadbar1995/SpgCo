package com.spg.sgpco.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.MaterialSpinner;
import com.spg.sgpco.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by a.Raghibdoust on 10/7/2017.
 */

public class TypeNameDialog extends Dialog {


    @BindView(R.id.spTypeProject)
    MaterialSpinner spTypeProject;
    @BindView(R.id.edtName)
    AppCompatEditText edtName;
    @BindView(R.id.btnOk)
    BaseTextView btnOk;
    @BindView(R.id.btnCancel)
    BaseTextView btnCancel;

    public TypeNameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(getContext(), R.layout.type_name_dialog, null);
        setContentView(view);


        ButterKnife.bind(this, view);
        setCancelable(false);


//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = -1;
//        getWindow().setAttributes(params);
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


    @OnClick({R.id.spTypeProject, R.id.btnOk, R.id.btnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.spTypeProject:
                break;
            case R.id.btnOk:


                SelectCityDialog selectCityDialog = new SelectCityDialog(getContext());
                selectCityDialog.show();
                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
