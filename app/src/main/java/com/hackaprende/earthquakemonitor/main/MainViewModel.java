package com.hackaprende.earthquakemonitor.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hackaprende.earthquakemonitor.Earthquake;
import com.hackaprende.earthquakemonitor.database.EqDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final MainRepository repository;
    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        EqDatabase database = EqDatabase.getDatabase(application);
        this.repository = new MainRepository(database);
    }

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    public void getEarthquakes() {
        repository.getEarthquakes(earthquakeList -> {
            eqList.setValue(earthquakeList);
        });
    }
}
