package com.hackaprende.earthquakemonitor;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hackaprende.earthquakemonitor.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("casdnciao", "Buenos Aires", 5.0, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("asdcecedc", "Ciudad de México", 4.0, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("3dqwecads", "Lima", 1.6, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("4445vwerv", "Madrid", 3.2, 2361278687L, 105.23, 98.127));
        eqList.add(new Earthquake("6g4vwerf2", "Caracas", 0.7, 2361278687L, 105.23, 98.127));

        EqAdapter adapter = new EqAdapter();
        adapter.setOnItemClickListener(earthquake ->
                Toast.makeText(MainActivity.this, earthquake.getPlace(),
                        Toast.LENGTH_SHORT).show());

        binding.eqRecycler.setAdapter(adapter);
        adapter.submitList(eqList);
    }
}