package com.spg.sgpco.baseView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.spg.sgpco.R;
import com.spg.sgpco.enums.FontTypeEnum;
import com.spg.sgpco.utils.Constants;

public class BaseTextView extends AppCompatTextView {


    public BaseTextView(Context context) {
        this(context, null);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setTextDirection(Constants.language.setTextDirec());
        }
        setFontType(FontTypeEnum.REGULAR);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseTextView, 0, 0);
            if (a.hasValue(R.styleable.BaseTextView_fontType)) {
                int value = a.getInt(R.styleable.BaseTextView_fontType, 0);
                if (value >= 0 && value < FontTypeEnum.values().length) {
                    setFontType(FontTypeEnum.values()[value]);
                }
            }
            a.recycle();
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


    @Override
    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_4));
    }

}