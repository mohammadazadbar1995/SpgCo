package com.spg.sgpco.model;

/**
 * Created by R.taghizadeh on 9/27/2017.
 */

public class Range {
    int Value;
    String Text;

    public Range(int value, String text) {
        Value = value;
        Text = text;
    }
    public int getValue() {
        return Value;
    }
    public void setValue(int value) {
        Value = value;
    }
    public String getText() {
        return Text;
    }
    public void setText(String text) {
        Text = text;
    }
}
