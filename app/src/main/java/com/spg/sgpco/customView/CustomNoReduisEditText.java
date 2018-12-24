package com.spg.sgpco.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseEditText;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.enums.InputTypeEnum;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.listener.OnEditTextChangeListener;
import com.spg.sgpco.utils.Constants;


public class CustomNoReduisEditText extends LinearLayout implements OnEditTextChangeListener {
    BaseTextView tvTitle;
    BaseEditText edtBody;
    BaseImageView image, edtIcon, imageInfo;

    private String error;
    private InputTypeEnum inputTypeEnum;


    public CustomNoReduisEditText(Context context) {
        this(context, null);
    }

    public CustomNoReduisEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomNoReduisEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.custom_no_reduis_edit_text, this, true);
        tvTitle = findViewById(R.id.tvTitle);
        edtBody = findViewById(R.id.edtBody);
        image = findViewById(R.id.image);
        edtIcon = findViewById(R.id.edtIcon);
        imageInfo = findViewById(R.id.infoImage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setLayoutDirection(Constants.language.getLayoutDirection());
        }

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0, 0);
            String titleText = a.getString(R.styleable.CustomEditText_tvTitle);
            String bodyValue = a.getString(R.styleable.CustomEditText_edtBody);
            String hint = a.getString(R.styleable.CustomEditText_hint);
            Boolean isRequired = a.getBoolean(R.styleable.CustomEditText_isRequired, false);
            Boolean isImageInfo = a.getBoolean(R.styleable.CustomEditText_isImageInfo, false);
            int icon = a.getResourceId(R.styleable.CustomEditText_edtIcon, 0);
            if (a.hasValue(R.styleable.CustomEditText_inputType)) {
                int value = a.getInt(R.styleable.CustomEditText_inputType, 0);

                if (value >= 0 && value < InputTypeEnum.values().length) {
                    setTypeEnum(InputTypeEnum.values()[value]);
                }
            }

            a.recycle();


            setTextsTitle(titleText);
            setEdtIcon(icon);
//            setTextValue(bodyValue);
            setTextHint(hint);
            setIsRequired(isRequired);
            setImageInfo(isImageInfo);
            imageInfo.setOnClickListener(view -> {
                CustomDialog customDialog = new CustomDialog(getContext());
                customDialog.setOkListener(getResources().getString(R.string.attention), view1 -> customDialog.dismiss());
                customDialog.setIcon(R.drawable.ic_info);
                customDialog.setDescription(getResources().getString(R.string.info));
                customDialog.setDialogTitle(getResources().getString(R.string.cold_area));
                customDialog.show();

            });
        }
        edtBody.onEditTextChangeListener = this;

    }

    private void setImageInfo(Boolean isImageInfo) {
        if (isImageInfo) {
            imageInfo.setVisibility(VISIBLE);
        } else {
            imageInfo.setVisibility(GONE);
        }
    }


    public void setUnit(String unit) {
        this.edtBody.unit = unit;
    }

    public void setTextHint(String hint) {
        edtBody.setHint(hint);
    }

    public void setBody(String body) {
        edtBody.setText(body);
    }

    public void setTypeEnum(InputTypeEnum inputTypeEnum) {
        this.inputTypeEnum = inputTypeEnum;
        this.edtBody.setInputTypeEnum(inputTypeEnum);
    }

    public InputTypeEnum getTypeEnum() {
        return inputTypeEnum;
    }

    public void setIsRequired(Boolean isRequired) {
        if (isRequired) {
            image.setVisibility(VISIBLE);
            error = getResources().getString(R.string.enter_title, tvTitle.getText().toString().trim());
            image.setImageResource(R.drawable.ic_asterisk);
        } else {
            image.setVisibility(GONE);
            error = null;
        }
        this.edtBody.setIsRequired(isRequired);
    }

    public void setTextsTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(title);
            edtBody.title = title;
        } else {
            tvTitle.setVisibility(INVISIBLE);

        }
    }

    private void setEdtIcon(int icon) {
        if (icon > 0) {
            edtIcon.setVisibility(VISIBLE);
            edtIcon.setImageResource(icon);
        } else {
            edtIcon.setVisibility(GONE);
        }
    }


//    public void setTextValue(String value) {
//        edtBody.setText(value);
//    }

//    public String getTextVal() {
//        if (edtBody.getText() == null) {
//            return "";
//        }
//        return edtBody.getText().toString().trim();
//    }

    @Override
    public void onGetError(String error) {
        setError(error);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        if (error != null) {
            image.setVisibility(VISIBLE);
            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_asterisk));
        } else if (!"".equals(edtBody.getTrimedText())) {
            image.setVisibility(VISIBLE);
            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        } else {
            image.setVisibility(GONE);
        }
    }

    public String getValueString() {
        return edtBody.getValueString();
    }

    public int getValueInt() {
        return edtBody.getValueInt();
    }

    public long getValueLong() {
        return edtBody.getValueLong();
    }


}
