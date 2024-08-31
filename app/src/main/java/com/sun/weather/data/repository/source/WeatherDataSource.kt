package com.sun.weather.data.repository.source

import com.sun.weather.data.model.CurrentWeather
import com.sun.weather.data.model.FavouriteLocation
import com.sun.weather.data.model.HourlyForecast
import com.sun.weather.data.model.WeeklyForecast

interface WeatherDataSource {

    interface Local {
        suspend fun getSelectedLocation(key: String): String
        suspend fun isFavoriteLocationExists(
            cityName: String,
            countryName: String,
        ): Boolean

        suspend fun getCurrentWeatherLocal()
        suspend fun saveCurrentWeather(currentWeather: CurrentWeather)
        suspend fun getWeeklyForecastLocal(city: String)
        suspend fun saveWeeklyForecastLocal(weeklyForecast: WeeklyForecast)
        suspend fun getHourlyForecastLocal(city: String)
        suspend fun saveHourlyForecastLocal(hourlyForecast: HourlyForecast)
        suspend fun insertFavoriteWeather(favouriteLocation: FavouriteLocation)
        suspend fun getAllFavorite(): List<FavouriteLocation>
        suspend fun removeFavoriteItem(id: Long)
    }

    interface Remote {
        suspend fun getCurrentWeather(city: String, language: String): CurrentWeather
        suspend fun getCurrentLocationWeather(
            lat: Double,
            lon: Double,
            language: String
        ): CurrentWeather

        suspend fun getHourlyForecast(city: String, language: String): HourlyForecast
        suspend fun getWeeklyForecast(city: String, language: String): WeeklyForecast
    }
}

