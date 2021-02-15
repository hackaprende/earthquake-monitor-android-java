package com.hackaprende.earthquakemonitor.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hackaprende.earthquakemonitor.Earthquake;

import java.util.List;

@Dao
interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> eqList);

    @Query("SELECT * FROM earthquakes")
    List<Earthquake> getEarthquakes();
}
