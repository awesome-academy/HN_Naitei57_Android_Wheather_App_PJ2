package com.sun.weather.data.repository

import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.model.toWeatherEntity
import com.sun.weather.data.repository.source.WeatherDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class WeatherRepositoryImpl(
    private val localDataSource: WeatherDataSource.Local,
    private val remoteDataSource: WeatherDataSource.Remote,
) : KoinComponent, WeatherRepository {
    override fun getCurrentWeather(
        city: String,
        language: String,
    ): Flow<WeatherEntity> {
        return flow {
            emit(remoteDataSource.getCurrentWeather(city, language).toWeatherEntity())
        }
    }

    override fun getCurrentLocationWeather(
        lat: Double,
        lon: Double,
        language: String,
    ): Flow<WeatherEntity> {
        return flow {
            emit(remoteDataSource.getCurrentLocationWeather(lat, lon, language).toWeatherEntity())
        }
    }

    override fun getHourlyForecast(
        city: String,
        language: String,
    ): Flow<WeatherEntity> {
        return flow {
            emit(remoteDataSource.getHourlyForecast(city, language).toWeatherEntity())
        }
    }

    override fun getWeeklyForecast(
        city: String,
        language: String,
    ): Flow<WeatherEntity> {
        return flow {
            emit(remoteDataSource.getWeeklyForecast(city, language).toWeatherEntity())
        }
    }
}
