package com.sun.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.repository.WeatherRepository
import com.sun.weather.utils.livedata.SingleLiveData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    var currentWeather = SingleLiveData<WeatherEntity>()
    var isLoading = SingleLiveData<Boolean>()
    var errorMessage = SingleLiveData<String>()

    fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double,
        language: String,
    ) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val weatherDeferred =
                    async { weatherRepository.getCurrentLocationWeather(latitude, longitude, language) }
                currentWeather.postValue(weatherDeferred.await().singleOrNull())
                isLoading.postValue(false)
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                isLoading.postValue(false)
            }
        }
    }

    fun fetchWeatherForSearchResult(
        location: String,
        language: String,
    ) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val weatherDeferred =
                    async { weatherRepository.getCurrentWeather(location, language) }
                currentWeather.postValue(weatherDeferred.await().singleOrNull())
                isLoading.postValue(false)
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                isLoading.postValue(false)
            }
        }
    }
}
