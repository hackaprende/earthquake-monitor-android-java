package com.hackaprende.earthquakemonitor.main;

import androidx.lifecycle.LiveData;

import com.hackaprende.earthquakemonitor.Earthquake;
import com.hackaprende.earthquakemonitor.api.EarthquakeJSONResponse;
import com.hackaprende.earthquakemonitor.api.EqApiClient;
import com.hackaprende.earthquakemonitor.api.Feature;
import com.hackaprende.earthquakemonitor.database.EqDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MainRepository {

    private final EqDatabase database;

    public MainRepository(EqDatabase database) {
        this.database = database;
    }

    public LiveData<List<Earthquake>> getEqList() {
        return database.eqDao().getEarthquakes();
    }

    public void downloadAndSaveEarthquakes() {
        EqApiClient.EqService service = EqApiClient.getInstance().getService();

        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {
                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());

                EqDatabase.databaseWriteExecutor.execute(() -> {
                    database.eqDao().insertAll(earthquakeList);
                });
            }

            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) {

            }
        });
    }

    private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJSONResponse body) {
        ArrayList<Earthquake> eqList = new ArrayList<>();

        List<Feature> features = body.getFeatures();
        for (Feature feature: features) {
            String id = feature.getId();
            double magnitude = feature.getProperties().getMag();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();

            double longitude = feature.getGeometry().getLongitude();
            double latitude = feature.getGeometry().getLatitude();
            Earthquake earthquake = new Earthquake(id, place, magnitude, time,
                    latitude, longitude);
            eqList.add(earthquake);
        }

        return eqList;
    }
}
