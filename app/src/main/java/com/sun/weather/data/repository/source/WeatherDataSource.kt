package com.sun.weather.data.repository.source

import com.sun.weather.data.model.CurrentWeather
import com.sun.weather.data.model.HourlyForecast
import com.sun.weather.data.model.Weather
import com.sun.weather.data.model.WeeklyForecast

interface WeatherDataSource {
    interface Local {
        suspend fun insertCurrentWeather(
            current: Weather,
            hourly: Weather,
            daily: Weather,
        )

        suspend fun insertCurrentWeather(weather: Weather)

        suspend fun getAllLocalWeathers(): List<Weather>

        suspend fun getLocalWeather(id: String): Weather?

        suspend fun deleteWeather(id: String)
    }

    interface Remote {
        suspend fun getCurrentWeather(
            city: String,
            language: String,
        ): CurrentWeather

        suspend fun getCurrentLocationWeather(
            lat: Double,
            lon: Double,
            language: String,
        ): CurrentWeather

        suspend fun getHourlyForecast(
            city: String,
            language: String,
        ): HourlyForecast

        suspend fun getWeeklyForecast(
            city: String,
            language: String,
        ): WeeklyForecast
    }
}
