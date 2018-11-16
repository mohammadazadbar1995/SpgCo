package com.spg.sgpco.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by R.taghizadeh on 9/21/2017.
 */
@Entity(tableName = "Customer")
public class Customer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;
    private String Name;
    private String Date;


    public Customer() {

    }

    public Customer(int id, String name, String date) {
        Id = id;
        Name = name;
        Date = date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }
}
