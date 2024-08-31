package com.sun.weather.data.repository

import com.sun.weather.data.model.FavouriteLocation
import com.sun.weather.data.model.Weather
import com.sun.weather.data.model.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun saveCurrentWeather(currentWeather: WeatherEntity)
    suspend fun saveWeeklyForecastLocal(weeklyForecast: WeatherEntity)
    suspend fun getLocalWeather(id: String): Weather?
    suspend fun saveHourlyForecastLocal(hourlyForecast: WeatherEntity)
    suspend fun insertFavoriteWeather(favouriteLocation: FavouriteLocation)
    suspend fun getAllFavorite(): List<FavouriteLocation>
    suspend fun removeFavoriteItem(id: Long)
    suspend fun isFavoriteLocationExists(cityName: String): Int
    fun getCurrentWeather(city: String, language: String): Flow<WeatherEntity>
    fun getCurrentLocationWeather(lat: Double, lon: Double, language: String): Flow<WeatherEntity>
    fun getHourlyForecast(city: String, language: String): Flow<WeatherEntity>
    fun getWeeklyForecast(city: String, language: String): Flow<WeatherEntity>
}
