package com.sun.weather.data.repository.source.local

import com.sun.weather.data.model.FavouriteLocation
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.local.dao.FavouriteDao
import com.sun.weather.data.repository.source.local.dao.WeatherDao

class WeatherLocalDataSource(
    private val weatherDao: WeatherDao,
    private val favouriteDao: FavouriteDao,
) : WeatherDataSource.Local {
    override suspend fun insertCurrentWeather(
        current: WeatherEntity,
        hourly: WeatherEntity,
        daily: WeatherEntity,
    ) {
        // Todo implement later
    }

    override suspend fun insertCurrentWeather(weather: WeatherEntity) {
        // Todo implement later
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

    override suspend fun insertFavourite(favourite: FavouriteLocation) {
        favouriteDao.insertFavourite(favourite)
    }

    override suspend fun getAllFavourite(): List<FavouriteLocation> {
        return favouriteDao.getAllFavourite()
    }

    override suspend fun removeFavouriteItem(favouriteId: Long) {
        favouriteDao.removeFavouriteItem(favouriteId)
    }

    override suspend fun isFavoriteLocationExists(cityName: String): Int {
        return favouriteDao.countCityByName(cityName)
    }
}
