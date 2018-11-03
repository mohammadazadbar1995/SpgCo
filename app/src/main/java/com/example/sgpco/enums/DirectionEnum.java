package com.example.sgpco.enums;

public enum DirectionEnum {

    RTL("راست به چپ", 1),
    LTR("چپ به راست", 2);

    private String methodName;
    private int methodCode;

    DirectionEnum(String methodName, int methodCode) {
        this.methodName = methodName;
        this.methodCode = methodCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getMethodCode() {
        return methodCode;
    }
}
