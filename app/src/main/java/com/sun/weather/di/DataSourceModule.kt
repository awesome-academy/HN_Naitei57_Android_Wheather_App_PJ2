package com.sun.weather.di

import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.local.WeatherLocalDataSource
import com.sun.weather.data.repository.source.remote.WeatherRemoteDataSource
import com.sun.weather.utils.Constant.BASE_API_SERVICE
import com.sun.weather.utils.Constant.PRO_API_SERVICE
import org.koin.core.qualifier.named
import org.koin.dsl.module

val DataSourceModule =
    module {
        single<WeatherDataSource.Remote> {
            WeatherRemoteDataSource(
                baseApiService = get(named(BASE_API_SERVICE)),
                proApiService = get(named(PRO_API_SERVICE)),
            )
        }
        single<WeatherDataSource.Local> { WeatherLocalDataSource(get(), get()) }
    }
