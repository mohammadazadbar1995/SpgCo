package com.spg.sgpco.enums;

import android.text.InputType;

public enum TypeEnum {
    TYPE_PROJECT(0),
    HEAT_SOURCE(1),
    GENDER_FLOOR(2),
    TYPE_SPACE(3);
//    TRANSIT_WIDTH مشارکت در ساخت دیده نشده در اپ

    private int methodCode;

    TypeEnum(int methodCode) {
        this.methodCode = methodCode;
    }


}
