package com.azurebear.myapplication2.network

import androidx.lifecycle.LiveData
import com.azurebear.myapplication2.model.CurrentWeather

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeather>

    suspend fun fetchCurrentWeather(location: String)

}