package com.spg.sgpco.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Customer.class
}, version = 1, exportSchema = false)


public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            AppDatabase.class.getName())
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    private static AppDatabase INSTANCE;

    public abstract CustomerDao customerDao();


    public static void destroyInstance() {
        INSTANCE = null;
    }

}