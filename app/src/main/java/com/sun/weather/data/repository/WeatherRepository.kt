package com.sun.weather.data.repository.source

import com.sun.weather.data.model.CurrentWeather
import com.sun.weather.data.model.HourlyForecast
import com.sun.weather.data.model.WeeklyForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCurrentWeather(city: String, language: String): Flow<CurrentWeather>
    fun getCurrentLocationWeather(lat: Double, lon: Double, language: String): Flow<CurrentWeather>
    fun getHourlyForecast(city: String, language: String): Flow<HourlyForecast>
    fun getWeeklyForecast(city: String, language: String): Flow<WeeklyForecast>
}
