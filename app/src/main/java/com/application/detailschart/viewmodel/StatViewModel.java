package com.application.detailschart.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.detailschart.model.StatLocal;
import com.application.detailschart.repository.StatRepository;

import java.util.List;

public class StatViewModel extends ViewModel {

    private StatRepository statRepository;
    private MutableLiveData<List<StatLocal>> mAllItems = new MutableLiveData<>();
    private boolean dataUpdated = false;

    public boolean isDataUpdated() {
        return dataUpdated;
    }

    public void setDataUpdated(boolean dataUpdated) {
        this.dataUpdated = dataUpdated;
    }

    public void initialise(Application application) {
        statRepository = new StatRepository(application);
    }

    public LiveData<List<StatLocal>> getAll() {
        return mAllItems;
    }

    public void getData()
    {
        List<StatLocal> list = statRepository.getAll();
        mAllItems.postValue(list);
    }
    public void insert(StatLocal statLocal) {
        statRepository.insert(statLocal);
    }

    public void deleteAll() {
        statRepository.deleteAll();
    }

    public void updateValue(int priority, String stat) {
        statRepository.updateValue(priority, stat);
    }


    public void setList(List<StatLocal> listData) {
        mAllItems.postValue(listData);
    }
}
