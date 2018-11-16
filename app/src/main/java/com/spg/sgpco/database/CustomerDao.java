package com.spg.sgpco.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CustomerDao {

    @Query("select * from  customer order by date Desc")
    List<Customer> customer();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllList(List<Customer> countries);

    @Insert(onConflict = REPLACE)
    void insertCustomer(Customer customer);


    @Query("DELETE FROM customer")
    public void nukeTable();


    @Delete
    void delete(Customer customer);
}
