package com.spg.sgpco.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseEditText;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.enums.InputTypeEnum;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.listener.OnEditTextChangeListener;
import com.spg.sgpco.utils.Constants;


public class CustomMultiLineEditText extends LinearLayout implements OnEditTextChangeListener {
    private BaseTextView tvTitle;
    private BaseEditText edtBody;
    private BaseImageView image;
    private String error;
    private InputTypeEnum inputTypeEnum;


    public CustomMultiLineEditText(Context context) {
        this(context, null);
    }

    public CustomMultiLineEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomMultiLineEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_multiline_edit_text, this, true);
        tvTitle = findViewById(R.id.tvTitle);
        edtBody = findViewById(R.id.edtBody);
        image = findViewById(R.id.image);
        this.setLayoutDirection(Constants.language.getLayoutDirection());

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMultiLineEditText, 0, 0);
            String titleText = a.getString(R.styleable.CustomMultiLineEditText_tvTitleMulti);
            String bodyValue = a.getString(R.styleable.CustomMultiLineEditText_edtBodyMulti);
            String hint = a.getString(R.styleable.CustomMultiLineEditText_hintMulti);
            Boolean isRequired = a.getBoolean(R.styleable.CustomMultiLineEditText_isRequiredMulti, false);
            if (a.hasValue(R.styleable.CustomMultiLineEditText_inputTypeMulti)) {
                int value = a.getInt(R.styleable.CustomMultiLineEditText_inputTypeMulti, 0);

                if (value >= 0 && value < TypeEnum.values().length) {
                    setTypeEnum(InputTypeEnum.values()[value]);
                }
            }
            a.recycle();
            setTextsTitle(titleText);
            setTextValue(bodyValue);
            setTextHint(hint);
            setIsRequired(isRequired);
        }
        edtBody.onEditTextChangeListener = this;

    }

    public void setTextHint(String hint) {
        edtBody.setHint(hint);
    }

    public void setTypeEnum(InputTypeEnum inputTypeEnum) {
        this.edtBody.setInputTypeEnum(inputTypeEnum);
        this.inputTypeEnum = inputTypeEnum;
    }

    public InputTypeEnum getInput() {
        return inputTypeEnum;
    }

    public void setIsRequired(Boolean isRequired) {
        if (isRequired) {
            image.setVisibility(VISIBLE);
            error = getResources().getString(R.string.enter_title, tvTitle.getText().toString().trim());
            image.setImageResource(R.drawable.ic_asterisk);
        }
        this.edtBody.setIsRequired(isRequired);
    }

    public void setTextsTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(title);
            edtBody.title = title;
        } else {
            tvTitle.setVisibility(GONE);

        }
    }

    public void setTextValue(String value) {
        edtBody.setText(value);
    }

    public String getTextVal() {
        if (edtBody.getText() == null) {
            return "";
        }
        return edtBody.getText().toString().trim();
    }

    @Override
    public void onGetError(String error) {
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

    public String getError() {
        return error;
    }

    public String getValue() {
        return edtBody.getValueString();
    }

}
