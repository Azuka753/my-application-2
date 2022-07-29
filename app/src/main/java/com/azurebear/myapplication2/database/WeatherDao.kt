package com.azurebear.myapplication2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.azurebear.myapplication2.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCurrentWeather(currentWeather: CurrentWeather)

    @Query("SELECT * FROM current_weather_table WHERE id = 0")
    fun getCurrentWeather(): Flow<CurrentWeather>
}