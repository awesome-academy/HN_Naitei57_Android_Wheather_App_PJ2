package com.sun.weather

import android.app.Application
import com.sun.weather.di.AppModule
import com.sun.weather.di.DataSourceModule
import com.sun.weather.di.NetworkModule
import com.sun.weather.di.RepositoryModule
import com.sun.weather.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class AndroidApplication : Application() {
    private val rootModule =
        listOf(AppModule, NetworkModule, DataSourceModule, RepositoryModule, ViewModelModule)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AndroidApplication)
            androidFileProperties()
            modules(rootModule)
        }
    }
}
