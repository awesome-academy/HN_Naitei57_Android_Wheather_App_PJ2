package com.sun.weather.data.repository.source.local

import com.sun.weather.data.model.Weather
import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.local.dao.WeatherDao

class WeatherLocalDataSource(
    private val weatherDao: WeatherDao,
) : WeatherDataSource.Local {
    override suspend fun insertCurrentWeather(
        current: Weather,
        hourly: Weather,
        daily: Weather,
    ) {
        // TODO LATER
    }

    override suspend fun insertCurrentWeather(weather: Weather) {
        // TODO LATER
    }

    override suspend fun getAllLocalWeathers(): List<Weather> {
        return weatherDao.getAllData()
    }

    override suspend fun getLocalWeather(id: String): Weather? {
        return weatherDao.getWeather(id)
    }

    override suspend fun deleteWeather(id: String) {
        weatherDao.deleteWeather(id)
    }
}
