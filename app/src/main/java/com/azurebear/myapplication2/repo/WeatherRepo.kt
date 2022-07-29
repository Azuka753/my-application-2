package com.azurebear.myapplication2.repo

import androidx.lifecycle.LiveData
import com.azurebear.myapplication2.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun getCurrentWeather(): Flow<CurrentWeather>
    fun refresh()
}