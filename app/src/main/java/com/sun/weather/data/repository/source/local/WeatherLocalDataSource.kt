package com.sun.weather.data.repository.source.local

import com.sun.weather.data.model.CurrentWeather
import com.sun.weather.data.model.FavouriteLocation
import com.sun.weather.data.model.HourlyForecast
import com.sun.weather.data.model.WeeklyForecast
import com.sun.weather.data.repository.source.WeatherDataSource
import com.sun.weather.data.repository.source.local.dao.WeatherDao

class WeatherLocalDataSource(  private val weatherDao: WeatherDao): WeatherDataSource.Local {
    override suspend fun getSelectedLocation(key: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun isFavoriteLocationExists(cityName: String, countryName: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeatherLocal() {
        TODO("Not yet implemented")
    }

    override suspend fun saveCurrentWeather(currentWeather: CurrentWeather) {
        TODO("Not yet implemented")
    }

    override suspend fun getWeeklyForecastLocal(city: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveWeeklyForecastLocal(weeklyForecast: WeeklyForecast) {
        TODO("Not yet implemented")
    }

    override suspend fun getHourlyForecastLocal(city: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHourlyForecastLocal(hourlyForecast: HourlyForecast) {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavoriteWeather(favouriteLocation: FavouriteLocation) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFavorite(): List<FavouriteLocation> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavoriteItem(id: Long) {
        TODO("Not yet implemented")
    }

}
