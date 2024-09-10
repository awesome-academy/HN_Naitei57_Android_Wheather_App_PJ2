package com.sun.weather.ui.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sun.weather.R
import com.sun.weather.data.model.FavouriteLocation
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.repository.WeatherRepository
import com.sun.weather.ui.detail.DetailFragment.Companion.MY_TAG
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    application: Application,
) : AndroidViewModel(application) {
    var currentWeather = MutableLiveData<WeatherEntity>()
    private var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

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

    fun addFavouriteLocation() {
        val context = getApplication<Application>().applicationContext
        currentWeather.value?.let { weather ->
            val cityName = weather.city
            val countryName = weather.country

            viewModelScope.launch {
                val cnt =
                    countryName?.let {
                        weatherRepository.isFavoriteLocationExists(it)
                    }
                if (cnt != null) {
                    if (cnt > 0) {
                        Toast.makeText(context, context.getString(R.string.already_favorite, cityName), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.add_favorite_success, cityName),
                            Toast.LENGTH_SHORT,
                        ).show()
                        val favouriteLocation = FavouriteLocation(cityName = cityName!!, countryName = countryName!!)
                        weatherRepository.insertFavoriteWeather(favouriteLocation)
                    }
                }
            }
        } ?: run {
            Log.v(MY_TAG, "Weather data is null, cannot add to favourites.")
        }
    }
}
