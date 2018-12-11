package com.spg.sgpco.model;

/**
 * Created by R.taghizadeh on 5/8/2018.
 */

public class MyMenuItem {
    int id;
    String title;
    int drawableId;
    int backColor;

    public MyMenuItem(int id, String title, int drawableId, int backColor) {
        this.id = id;
        this.title = title;
        this.drawableId = drawableId;
        this.backColor = backColor;
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
}
