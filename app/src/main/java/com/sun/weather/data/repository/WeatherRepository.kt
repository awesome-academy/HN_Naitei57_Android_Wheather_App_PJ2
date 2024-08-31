package com.sun.weather.data.repository

import com.sun.weather.data.model.FavouriteLocation
import com.sun.weather.data.model.Weather
import com.sun.weather.data.model.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getSelectedLocation(key: String): String

    fun isFavoriteLocationExists(
        cityName: String,
        countryName: String,
    ): Boolean

    fun saveCurrentWeather(currentWeather: WeatherEntity)

    fun saveWeeklyForecastLocal(weeklyForecast: WeatherEntity)

    fun getLocalWeather(id: String): Weather?

    fun saveHourlyForecastLocal(hourlyForecast: WeatherEntity)

    fun insertFavoriteWeather(favouriteLocation: FavouriteLocation)

    fun getAllFavorite(): List<FavouriteLocation>

    fun removeFavoriteItem(id: Long)

    fun getCurrentWeather(
        city: String,
        language: String,
    ): Flow<WeatherEntity>

    fun getCurrentLocationWeather(
        lat: Double,
        lon: Double,
        language: String,
    ): Flow<WeatherEntity>

    fun getHourlyForecast(
        city: String,
        language: String,
    ): Flow<WeatherEntity>

    fun getWeeklyForecast(
        city: String,
        language: String,
    ): Flow<WeatherEntity>
}
