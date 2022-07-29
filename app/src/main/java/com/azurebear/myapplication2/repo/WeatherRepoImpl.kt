package com.azurebear.myapplication2.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.azurebear.myapplication2.database.WeatherDao
import com.azurebear.myapplication2.model.CurrentWeather
import com.azurebear.myapplication2.network.WeatherNetworkDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class WeatherRepoImpl(
    private val weatherDao: WeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherRepo {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)

            Log.e("wow", it.toString())
        }
    }

    override fun getCurrentWeather(): Flow<CurrentWeather> {
        GlobalScope.launch(Dispatchers.IO){
            fetchCurrentWeather()
        }
        return weatherDao.getCurrentWeather()
    }

    override fun refresh(){
        GlobalScope.launch(Dispatchers.IO){
            fetchCurrentWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeather){
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.upsertCurrentWeather(fetchedWeather)
        }
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("101200101")
    }
}