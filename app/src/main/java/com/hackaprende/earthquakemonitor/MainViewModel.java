package com.hackaprende.earthquakemonitor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hackaprende.earthquakemonitor.api.EarthquakeJSONResponse;
import com.hackaprende.earthquakemonitor.api.EqApiClient;
import com.hackaprende.earthquakemonitor.api.Feature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    public void getEarthquakes() {
        EqApiClient.EqService service = EqApiClient.getInstance().getService();

        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {
                // List<Earthquake> earthquakeList = parseEarthquakes(response.body());

                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                eqList.setValue(earthquakeList);
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

    private List<Earthquake> parseEarthquakes(String responseString) {
        ArrayList<Earthquake> eqList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(responseString);
            JSONArray featuresJSONArray = jsonResponse.getJSONArray("features");

            for (int i = 0 ; i < featuresJSONArray.length() ; i++) {
                JSONObject jsonFeature = featuresJSONArray.getJSONObject(i);
                String id = jsonFeature.getString("id");
                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");

                double magnitude = jsonProperties.getDouble("mag");
                String place = jsonProperties.getString("place");
                long time = jsonProperties.getLong("time");

                JSONObject jsonGeometry = jsonFeature.getJSONObject("geometry");
                JSONArray coordinatesJSONArray = jsonGeometry.getJSONArray("coordinates");
                double longitude = coordinatesJSONArray.getDouble(0);
                double latitude = coordinatesJSONArray.getDouble(1);

                Earthquake earthquake = new Earthquake(id, place, magnitude, time, latitude, longitude);
                eqList.add(earthquake);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eqList;
    }
}
