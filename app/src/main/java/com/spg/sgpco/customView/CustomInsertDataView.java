package com.spg.sgpco.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.enums.DirectionEnum;
import com.spg.sgpco.utils.Constants;


public class CustomInsertDataView extends LinearLayout {

    private BaseTextView tvValue;
    private BaseImageView imgInfo, icon;
    private String title;
    private String hint;


    public CustomInsertDataView(Context context) {
        this(context, null);
    }

    public CustomInsertDataView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomInsertDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_insert_data, this, true);
        tvValue = findViewById(R.id.tvValue);
        imgInfo = findViewById(R.id.imgInfo);
        icon = findViewById(R.id.icon);
//        imgInfo.setVisibility(INVISIBLE);

        tvValue.setTextColor(getResources().getColor(R.color.secondryTextColor));
        tvValue.setText(title);
        this.setLayoutDirection(Constants.language.getLayoutDirection());

    }

    public void setImgInfo(int icon) {
        this.imgInfo.setImageResource(icon);
    }

    public void setTxtTitle(String title) {
        this.title = title;
    }

    public void reset() {
        String htmlrerst = " <font color='#333333'>" + title + "</font> \t\t " +
                "<font color='#757575'>" + hint + "</font>";
        createHtmlText(htmlrerst);
        enable();
        setError(null);
    }

    public void setValue(String value) {
        String htmlText = "<font color='#333333'>" + title + "</font>"
                + "<font color='#757575'>\t\t" + value;
        htmlText += "</font>";
        createHtmlText(htmlText);
        enable();
        setError(null);
    }

    public void setHint(String hint) {
        this.hint = hint;
    }


    public void enable() {
        icon.setVisibility(VISIBLE);
        this.setEnabled(true);
        this.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void setError(String error) {
        icon.setVisibility(VISIBLE);
        if (error != null) {
            icon.setImageResource(R.drawable.ic_asterisk);
        } else {
            if (Constants.language.getDirection() == DirectionEnum.LTR) {
                icon.setImageResource(R.drawable.ic_keyboard_english);
            } else {
                icon.setImageResource(R.drawable.ic_keyboard_arrow);
            }

        }
    }

    private void createHtmlText(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvValue.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvValue.setText(Html.fromHtml(text));
        }
    }


}
