package com.sun.weather.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.utils.livedata.SingleLiveData
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    var currentWeather = SingleLiveData<WeatherEntity>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double,
    ) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val weatherDeferred =
                    async { weatherRepository.getCurrentLocationWeather(latitude, longitude, "vi") }
                currentWeather.postValue(weatherDeferred.await().singleOrNull())
                isLoading.postValue(false)
            } catch (e: Exception) {
                Log.v("LCD", "Tai View Model:\n" + e.message.toString())
                errorMessage.postValue(e.message)
                isLoading.postValue(false)
            }
        }
    }
}
