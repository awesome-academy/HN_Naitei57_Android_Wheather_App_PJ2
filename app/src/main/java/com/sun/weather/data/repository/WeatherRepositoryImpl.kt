package com.sun.weather.data.repository

import com.sun.weather.data.model.CurrentWeather
import com.sun.weather.data.model.HourlyForecast
import com.sun.weather.data.model.WeeklyForecast
import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class WeatherRepositoryImpl(
    private val localDataSource: WeatherDataSource.Local,
    private val remoteDataSource: WeatherDataSource.Remote
) : KoinComponent, WeatherRepository {

    override fun getCurrentWeather(city: String, language: String): Flow<CurrentWeather> {
        return flow {
            emit(remoteDataSource.getCurrentWeather(city, language))
        }
    }

    override fun getCurrentLocationWeather(lat: Double, lon: Double, language: String): Flow<CurrentWeather> {
        return flow {
            emit(remoteDataSource.getCurrentLocationWeather(lat, lon, language))
        }
    }

    override fun getHourlyForecast(city: String, language: String): Flow<HourlyForecast> {
        return flow {
            emit(remoteDataSource.getHourlyForecast(city, language))
        }
    }

    override fun getWeeklyForecast(city: String, language: String): Flow<WeeklyForecast> {
        return flow {
            emit(remoteDataSource.getWeeklyForecast(city, language))
        }
    }
}
