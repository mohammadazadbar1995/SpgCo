package com.spg.sgpco.enums;

import android.text.InputType;

public enum InputTypeEnum {
    DESCRIPTION(0),
    NAME(1),
    MOBILE(2),
    PHONE(3),
    PASSWORD(4),
    CODEVERIFY(5),
    NUMBER(6);

//    TRANSIT_WIDTH مشارکت در ساخت دیده نشده در اپ

    private int methodCode;

    InputTypeEnum(int methodCode) {
        this.methodCode = methodCode;
    }


    public int getCharCount() {
        switch (this) {
            case DESCRIPTION:
                return 500;
            case NAME:
                return 50;
            case MOBILE:
            case PHONE:
                return 11;
            case PASSWORD:
                return 11;
            case CODEVERIFY:
                return 6;
            case NUMBER:
                return 10;

        }
        return 11;
    }

    public int getKeyBoardType() {
        switch (this) {
            case DESCRIPTION:
            case NAME:
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
            case MOBILE:
            case PHONE:

                return InputType.TYPE_CLASS_NUMBER;
            case PASSWORD:
                return InputType.TYPE_CLASS_NUMBER;
            case CODEVERIFY:
                return InputType.TYPE_CLASS_NUMBER;
            case NUMBER:
                return InputType.TYPE_CLASS_NUMBER;
        }

        return InputType.TYPE_CLASS_TEXT;
    }


    public boolean isNumeric() {
        switch (this) {

        }
        return false;
    }


    public int getMaxLine() {
        if (this == DESCRIPTION) {
            return 6;//zamani k description bood max line 6
        }
        return 1;// sayer mavared max line 1
    }
}
