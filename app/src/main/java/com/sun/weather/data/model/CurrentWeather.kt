package com.sun.weather.data.model

import com.google.gson.annotations.SerializedName
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.utils.ext.combineWithCountry

data class CurrentWeather(
    @SerializedName("main")
    var main: Main,
    @SerializedName("weather")
    var weathers: List<Weather>,
    @SerializedName("wind")
    var wind: Wind,
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("dt")
    var dt: Long,
    @SerializedName("name")
    var nameCity: String,
    @SerializedName("sys")
    var sys: Sys,
    var day: String = "",
    var iconWeather: String = "",
)

data class Main(
    @SerializedName("temp")
    var currentTemperature: Double,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("temp_min") var tempMin: Double,
    @SerializedName("temp_max") var tempMax: Double,
)

data class Weather(
    @SerializedName("icon")
    var iconWeather: String,
    @SerializedName("main")
    var main: String,
    @SerializedName("description")
    var description: String,
)

data class Wind(
    @SerializedName("speed")
    var windSpeed: Double,
)

data class Clouds(
    @SerializedName("all")
    var percentCloud: Int,
)

data class Coord(
    @SerializedName("lon")
    var lon: Double,
    @SerializedName("lat")
    var lat: Double,
)

data class Sys(
    @SerializedName("country")
    var country: String,
)

fun CurrentWeather.toWeatherEntity(): WeatherEntity {
    val country: String? = sys.country
    val id = nameCity.combineWithCountry(country)
    val latitude = coord.lat
    val longitude = coord.lon
    val timeZone = dt
    val city = nameCity
    val weatherCurrent: WeatherBasic? = this.toWeatherBasic()
    val weatherHourlyList: List<WeatherBasic>? = null
    val weatherDailyList: List<WeatherBasic>? = null

    return WeatherEntity(
        id = id,
        latitude = latitude,
        longitude = longitude,
        timeZone = timeZone,
        city = city,
        country = country,
        weatherCurrent = weatherCurrent,
        weatherHourlyList = weatherHourlyList,
        weatherDailyList = weatherDailyList,
    )
}

fun CurrentWeather.toWeatherBasic(): WeatherBasic {
    return WeatherBasic(
        dateTime = dt,
        currentTemperature = main.currentTemperature,
        maxTemperature = main.tempMax,
        minTemperature = main.tempMin,
        iconWeather = weathers.firstOrNull()?.iconWeather,
        weatherDescription = weathers.firstOrNull()?.description,
        humidity = main.humidity,
        percentCloud = clouds.percentCloud,
        windSpeed = wind.windSpeed,
    )
}
