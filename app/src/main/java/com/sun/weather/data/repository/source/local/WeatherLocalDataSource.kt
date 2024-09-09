package com.sun.weather.data.repository.source.local

import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.local.dao.WeatherDao

class WeatherLocalDataSource(
    private val weatherDao: WeatherDao,
) : WeatherDataSource.Local {
    override suspend fun insertCurrentWeather(
        current: WeatherEntity,
        hourly: WeatherEntity,
        daily: WeatherEntity,
    ) {
    }

    override suspend fun insertCurrentWeather(weather: WeatherEntity) {
    }

    override suspend fun getAllLocalWeathers(): List<WeatherEntity> {
        return weatherDao.getAllData()
    }

    override suspend fun getLocalWeather(id: String): WeatherEntity? {
        return weatherDao.getWeather(id)
    }

    override suspend fun deleteWeather(id: String) {
        weatherDao.deleteWeather(id)
    }
}
