package com.spg.sgpco.service;

public enum ServerMethods {
    Login("Login", 0);
    private String methodName;
    private int methodValue;

    public int getMethodValue() {

        return this.methodValue;
    }

    private ServerMethods(String toString, int value) {
        this.methodName = toString;
        this.methodValue = value;
    }

    public String toString() {

        return this.methodName;
    }
}
