package com.spg.sgpco.model;

/**
 * Created by R.taghizadeh on 5/8/2018.
 */

public class MyMenuItem {
    private int imageColor;
    private int id;
    private String title;
    private int drawableId;
    private int backColor;

    public MyMenuItem(int id, String title, int drawableId, int backColor, int imageColor) {
        this.id = id;
        this.title = title;
        this.drawableId = drawableId;
        this.backColor = backColor;
        this.imageColor = imageColor;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getBackColor() {
        return backColor;
    }

    public int getImageColor() {
        return imageColor;
    }
}
