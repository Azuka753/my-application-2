package com.azurebear.myapplication2.ui.home_page

import android.util.Log
import androidx.lifecycle.*
import com.azurebear.myapplication2.model.CurrentWeather
import com.azurebear.myapplication2.repo.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    val currentWeather: LiveData<CurrentWeather> =
        weatherRepo.getCurrentWeather().asLiveData()

    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepo.refresh()
        }
    }
}

class HomeViewModelFactory(private val repository: WeatherRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}