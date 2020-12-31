package com.application.detailschart.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.application.detailschart.model.StatLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities ={StatLocal.class},version = 1,exportSchema = false)
public abstract class StatDatabase extends RoomDatabase {

    public abstract StatDao statDao();

    private static volatile StatDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
   public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

   public static StatDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (StatDatabase.class){
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StatDatabase.class,"stat_table").build();
                }
            }
        }
        return INSTANCE;
    }
}
