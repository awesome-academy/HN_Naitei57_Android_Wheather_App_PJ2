package com.sun.weather.di


import com.sun.weather.data.repository.WeatherRepositoryImpl
import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.WeatherRepository
import com.sun.weather.data.repository.source.local.WeatherLocalDataSource
import com.sun.weather.data.repository.source.remote.WeatherRemoteDataSource
import org.koin.dsl.module

val RepositoryModule = module {
    single {
        provideWeatherRepository(
            WeatherLocalDataSource(get()),
            WeatherRemoteDataSource(get())
        )
    }
}

fun provideWeatherRepository(
    local: WeatherDataSource.Local,
    remote: WeatherDataSource.Remote
): WeatherRepository {
    return WeatherRepositoryImpl(local, remote)
}
