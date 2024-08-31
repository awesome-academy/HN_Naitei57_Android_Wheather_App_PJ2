package com.sun.weather.data.model

import com.google.gson.annotations.SerializedName
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.utils.ext.combineWithCountry

data class HourlyForecast(
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val forecastList: List<HourlyForecastItem>,
    @SerializedName("city") val city: City,
)

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("coord") val coord: Coord,
    @SerializedName("country") val country: String,
)

data class HourlyForecastItem(
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("dt_txt") val dtTxt: String,
    val iconWeather: String = "",
)

fun HourlyForecast.toWeather(): WeatherEntity {
    return WeatherEntity(
        id = city.name.combineWithCountry(city.country),
        latitude = city.coord.lat,
        longitude = city.coord.lon,
        city = city.name,
        country = city.country,
        weatherCurrent = null,
        weatherHourlyList = forecastList.map { it.toWeatherBasic() },
        weatherDailyList = null,
    )
}

fun HourlyForecastItem.toWeatherBasic(): WeatherBasic {
    return WeatherBasic(
        dateTime = dt,
        currentTemperature = main.currentTemperature,
        maxTemperature = main.tempMax,
        minTemperature = main.tempMin,
        iconWeather = weather.firstOrNull()?.iconWeather,
        weatherDescription = weather.firstOrNull()?.description,
        humidity = main.humidity,
        percentCloud = clouds.percentCloud,
        windSpeed = wind.windSpeed,
    )
}
