package com.sun.weather.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sun.weather.data.model.WeatherBasic
import com.sun.weather.data.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

class DetailViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weeklyForecast = MutableLiveData<List<WeatherBasic>>(emptyList())
    val weeklyForecast: LiveData<List<WeatherBasic>> get() = _weeklyForecast
    private val _hourlyForecast = MutableLiveData<List<WeatherBasic>>(emptyList())
    val hourlyForecast: LiveData<List<WeatherBasic>> get() = _hourlyForecast
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadWeeklyForecast(cityName: String, isNetworkAvailable: Boolean, language: String) {
        viewModelScope.launch {
            try {
                if (isNetworkAvailable) {
                    val weeklyDeferred = async { weatherRepository.getWeeklyForecast(cityName, language) }
                    _weeklyForecast.postValue(weeklyDeferred.await().singleOrNull()?.weatherDailyList.orEmpty())
                }
                else {
                    Log.v(DetailFragment.MY_TAG, "Network is not available for weekly forecast")
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun loadHourlyForecast(cityName: String, isNetworkAvailable: Boolean, language: String) {
        viewModelScope.launch {
            try {
                if (isNetworkAvailable) {
                    val hourlyDeferred = async { weatherRepository.getHourlyForecast(cityName, language) }
                    _hourlyForecast.postValue(hourlyDeferred.await().singleOrNull()?.weatherHourlyList.orEmpty())
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}
