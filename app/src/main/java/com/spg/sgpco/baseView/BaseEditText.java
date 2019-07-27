package com.spg.sgpco.baseView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.spg.sgpco.R;
import com.spg.sgpco.enums.FontTypeEnum;
import com.spg.sgpco.enums.InputTypeEnum;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.listener.OnEditTextChangeListener;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.Formatter;
import com.spg.sgpco.utils.Validation;

import java.text.DecimalFormat;
import java.text.ParseException;

public class BaseEditText extends AppCompatEditText implements TextWatcher {

    public OnEditTextChangeListener onEditTextChangeListener;
    public String title;
    public String error = null;
    public String unit = null;
    private InputTypeEnum inputType = InputTypeEnum.DESCRIPTION;
    private Boolean isRequired = false;

    public BaseEditText(Context context) {
        super(context, null);
        initView(context, null, 0);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setTextDirection(Constants.language.setTextDirec());
        }
        setFontType(FontTypeEnum.REGULAR);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseEditText, 0, 0);
            if (a.hasValue(R.styleable.BaseEditText_fontTypeEditText)) {
                int value = a.getInt(R.styleable.BaseEditText_fontTypeEditText, 0);
                if (value >= 0 && value < FontTypeEnum.values().length) {
                    setFontType(FontTypeEnum.values()[value]);
                }
            }
            a.recycle();
        }
        this.addTextChangedListener(this);
        this.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_2));
    }

    public String getTrimedText() {
        if (this.getText() != null) {
            return this.getText().toString().trim();
        }
        return "";
    }

    public void setInputTypeEnum(InputTypeEnum inputType) {
        this.inputType = inputType;
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputType.getCharCount())});
        this.setInputType(inputType.getKeyBoardType());
        this.setMaxLines(inputType.getMaxLine());
    }


    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (onEditTextChangeListener != null) {
            String text = "";
            if (charSequence != null) {
                text = charSequence.toString().trim().replaceAll(",", "");
            }
            switch (inputType) {

                case NAME:
                    error = Validation.getNameError(text, title, isRequired, getResources());
                    break;
                case MOBILE:
                    error = Validation.getMobileError(text, isRequired, getResources());
                    break;
                case PASSWORD:
                    error = Validation.getPasswordError(text, isRequired, getResources());
                    break;
                case CODEVERIFY:
                    error = Validation.getCodeVerify(text, isRequired, getResources());
                    break;
                case NUMBER:
                    error = Validation.getNumberError(text,title,isRequired,getResources());
                    break;
            }
            if (onEditTextChangeListener != null) {
                onEditTextChangeListener.onGetError(error);
            }


        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (inputType.isNumeric()) {
            this.removeTextChangedListener(this);
            String text = editable.toString();
            DecimalFormat formatrer = Formatter.getInstance();

            try {
                Formatter formatter = new Formatter();
                int inilen, endlen;
                inilen = text.length();
                char separator = formatrer.getDecimalFormatSymbols().getGroupingSeparator();
                String v = text.replace(String.valueOf(separator), "");
                Number n = formatter.df().parse(v);

                int cp = this.getSelectionStart();
                if (formatter.isHasFractionalPart()) {
                    this.setText(formatter.df().format(n));
                } else {
                    this.setText(formatter.dfnf().format(n));
                }
//                this.setText(Formatter.getInstance().format(n));
                endlen = this.getText().length();
                int sel = (cp + (endlen - inilen));
                if (sel > 0 && sel <= this.getText().length()) {
                    this.setSelection(sel);
                } else {
                    // place cursor at the end?
                    this.setSelection(this.getText().length() - 1);
                }
            } catch (NumberFormatException nfe) {
                // do nothing?
            } catch (ParseException e) {
                // do nothing?
            }

            this.addTextChangedListener(this);
        }

    }


    public void setFontType(FontTypeEnum fontType) {
        if (fontType == FontTypeEnum.BOLD) {
            this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansMobile(FaNum)_Bold.ttf"));
        } else if (fontType == FontTypeEnum.MEDIUM) {
            this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansMobile(FaNum)_Medium.ttf"));
        } else if (fontType == FontTypeEnum.Light) {
            this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansMobile(FaNum)_Light.ttf"));
        } else {
            this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansMobile(FaNum).ttf"));
        }
    }

    public String getValueString() {
        String text = getTrimedText();
        if (text.isEmpty()) {
            return null;
        }
        return getTrimedText();
    }

    public int getValueInt() {
        if (getTrimedText().isEmpty()) {
            return 0;
        }
        DecimalFormat formatter = Formatter.getInstance();
        char separator = formatter.getDecimalFormatSymbols().getGroupingSeparator();
        String v = getTrimedText().replace(String.valueOf(separator), "");
        try {
            return formatter.parse(v).intValue();
        } catch (ParseException e) {
            return 0;
        }
    }

    public long getValueLong() {
        if (getTrimedText().isEmpty()) {
            return 0;
        }
        DecimalFormat formatter = Formatter.getInstance();
        char separator = formatter.getDecimalFormatSymbols().getGroupingSeparator();
        String v = getTrimedText().replace(String.valueOf(separator), "");
        try {
            return formatter.parse(v).longValue();
        } catch (ParseException e) {
            return 0;
        }
    }

    @Override
    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

}
