package com.application.detailschart.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.application.detailschart.model.StatLocal;

import java.util.List;

@Dao
public interface StatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(StatLocal statLocal);

    @Query("SELECT * FROM 'stat_table'")
    List<StatLocal> getAll();

    @Query("DELETE FROM stat_table")
    void deleteAll();

    @Query("UPDATE 'stat_table'  set stat =:stat where priority =:priority ")
    void updateValue(int priority, String stat);


}
