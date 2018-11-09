package com.spg.sgpco.utils;

import android.util.LayoutDirection;
import android.view.View;

import com.spg.sgpco.enums.DirectionEnum;

import java.util.Locale;

public class Language {

    private String name;
    private String code;



    private DirectionEnum direction;


    public Language(String name, String code, DirectionEnum direction) {
        this.name = name;
        this.code = code;
        this.direction = direction;
    }

    public int getLayoutDirection() {
        if (direction == DirectionEnum.LTR) {
            return LayoutDirection.LTR;
        }

        return LayoutDirection.RTL;
    }

    public int setTextDirec() {
        if (direction == DirectionEnum.LTR) {
            return View.TEXT_DIRECTION_LTR;
        }
        return View.TEXT_DIRECTION_RTL;
    }

    public Locale getLocale() {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        return locale;
    }

    public DirectionEnum getDirection() {
        return direction;
    }


}
