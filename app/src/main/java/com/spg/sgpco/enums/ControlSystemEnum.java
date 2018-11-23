package com.spg.sgpco.enums;

public enum ControlSystemEnum {
    ORDINARY_SYSTEM(1),
    THERMOSTATIC_SYSTEM(2);
//    TRANSIT_WIDTH مشارکت در ساخت دیده نشده در اپ

    private int methodCode;

    ControlSystemEnum(int methodCode) {
        this.methodCode = methodCode;
    }

    public int getMethodCode() {
        return methodCode;
    }
}
