package com.sun.weather.data.repository.source

import com.sun.weather.data.model.CurrentWeather
import com.sun.weather.data.model.HourlyForecast
import com.sun.weather.data.model.WeeklyForecast
import com.sun.weather.data.model.entity.WeatherEntity

interface WeatherDataSource {
    interface Local {
        suspend fun insertCurrentWeather(
            current: WeatherEntity,
            hourly: WeatherEntity,
            daily: WeatherEntity,
        )

        suspend fun insertCurrentWeather(weather: WeatherEntity)

        suspend fun getAllLocalWeathers(): List<WeatherEntity>

        suspend fun getLocalWeather(id: String): WeatherEntity?

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
