package com.example.sgpco.enums;

import android.text.InputType;

public enum InputTypeEnum {
    DESCRIPTION(0),
    NAME(1),
    MOBILE(2),
    PHONE(3),
    EMAIL(4),
    RENT_PRICE(5),
    TOTAL_PRICE(6),
    MORTGAGE_PRICE(7),
    FLOOR(8),
    FLOOR_COUNT(9),
    ROOM_COUNT(10),
    YEAR_BUILT(11),
    AREA(12),
    TOTAL_AREA(13),
    ADDRESS(14),
    LOAN(15),
    GROUND_WIDTH(16);

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
            case EMAIL:
                return 320;
            case RENT_PRICE:
            case TOTAL_PRICE:
            case MORTGAGE_PRICE:
            case LOAN:
                return 15;
            case FLOOR:
            case FLOOR_COUNT:
            case ROOM_COUNT:
            case YEAR_BUILT:
                return 2;
            case AREA:
            case TOTAL_AREA:
            case GROUND_WIDTH:
                return 10;
            case ADDRESS:
                return 80;
        }
        return 11;
    }

    public int getKeyBoardType() {
        switch (this) {
            case DESCRIPTION:
            case NAME:
            case ADDRESS:
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
            case MOBILE:
            case PHONE:
            case RENT_PRICE:
            case TOTAL_PRICE:
            case MORTGAGE_PRICE:
            case GROUND_WIDTH:
            case LOAN:
            case FLOOR:
            case FLOOR_COUNT:
            case ROOM_COUNT:
            case YEAR_BUILT:
            case AREA:
            case TOTAL_AREA:
                return InputType.TYPE_CLASS_NUMBER;
            case EMAIL:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;

        }

        return InputType.TYPE_CLASS_TEXT;
    }


    public boolean isNumeric() {
        switch (this) {
            case RENT_PRICE:
            case TOTAL_PRICE:
            case MORTGAGE_PRICE:
            case GROUND_WIDTH:
            case LOAN:
            case AREA:
            case TOTAL_AREA:
                return true;
        }
        return false;
    }


    public int getMaxLine() {
        if (this == DESCRIPTION) {
            return 6;//zamani k description bood max line 6
        }
        if (this == ADDRESS) {
            return 2;//zamani k address bod max line 2
        }
        return 1;// sayer mavared max line 1
    }
}
