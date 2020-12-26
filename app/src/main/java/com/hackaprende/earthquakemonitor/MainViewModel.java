package com.hackaprende.earthquakemonitor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    public void getEarthquakes() {
        ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("casdnciao", "Buenos Aires", 5.0, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("asdcecedc", "Ciudad de México", 4.0, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("3dqwecads", "Lima", 1.6, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("4445vwerv", "Madrid", 3.2, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("6g4vwerf2", "Caracas", 0.7, 2361278687L, 105.23, 98.127));

        this.eqList.setValue(eqList);
    }
}
