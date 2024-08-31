package com.example.weather.di


import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.remote.WeatherRemoteDataSource
import org.koin.dsl.module

val DataSourceModule = module {
    single<WeatherDataSource.Remote> { WeatherRemoteDataSource(get()) }

}
