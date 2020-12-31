package com.application.detailschart.repository;

import android.app.Application;

import com.application.detailschart.local.StatDao;
import com.application.detailschart.local.StatDatabase;
import com.application.detailschart.model.StatLocal;

import java.util.List;


public class StatRepository {
    private  StatDao statDao;

    public StatRepository(Application application)
    {
        StatDatabase db = StatDatabase.getDatabase(application);
        statDao = db.statDao();
    }

    public List<StatLocal> getAll()
    {
        return statDao.getAll();
    }

    public void insert(StatLocal statLocal)
    {
        StatDatabase.databaseWriteExecutor.execute(() -> {
            statDao.insert(statLocal);
        });
    }

    public void deleteAll(){
        StatDatabase.databaseWriteExecutor.execute(() -> {
           statDao.deleteAll();
        });
    }


    public void updateValue(int priority,String stat)
    {
        StatDatabase.databaseWriteExecutor.execute(() -> {
            statDao.updateValue(priority,stat);
        });
    }


}
