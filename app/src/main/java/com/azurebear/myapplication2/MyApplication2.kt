package com.azurebear.myapplication2

import android.app.Application
import com.azurebear.myapplication2.database.WeatherRoomDatabase
import com.azurebear.myapplication2.network.*
import com.azurebear.myapplication2.repo.WeatherRepoImpl

class MyApplication2: Application() {
    val database by lazy { WeatherRoomDatabase.getDatabase(this) }
    val apiService by lazy { QWeatherApiService(ConnectivityInterceptorImpl(this)) }
    val dataSource by lazy { WeatherNetworkDataSourceImpl(apiService) }
    val repository by lazy { WeatherRepoImpl(database.weatherDao(), dataSource) }
}