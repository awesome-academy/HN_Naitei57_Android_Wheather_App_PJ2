package com.sun.weather.data.model

import com.example.weather.utils.ext.combineWithCountry
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sun.weather.data.model.entity.WeatherEntity


data class WeeklyForecast(
    @SerializedName("city")
    @Expose
    val city: City? = null,
    @SerializedName("cnt")
    @Expose
    val cnt: Int = 0,
    @SerializedName("list")
    @Expose
    val forecastList: List<WeeklyForecastItem>? = null,
)

data class WeeklyForecastItem(
    @SerializedName("dt")
    @Expose
    val dt: Long = 0,
    @SerializedName("temp")
    @Expose
    val temp: Temp? = null,
    @SerializedName("humidity")
    @Expose
    val humidity: Int = 0,
    @SerializedName("weather")
    @Expose
    val weather: List<Weather>? = null,
    @SerializedName("speed")
    @Expose
    val speed: Double = 0.0,
    @SerializedName("clouds")
    @Expose
    val clouds: Int = 0,
    val day: String? = "",
    val iconWeather: String? = "",
)

data class Temp(
    @SerializedName("day")
    @Expose
    val day: Double = 0.0,
    @SerializedName("min")
    @Expose
    val min: Double = 0.0,
    @SerializedName("max")
    @Expose
    val max: Double = 0.0,
    @SerializedName("night")
    @Expose
    val night: Double = 0.0,
    @SerializedName("eve")
    @Expose
    val eve: Double = 0.0,
    @SerializedName("morn")
    @Expose
    val morn: Double = 0.0,
)

fun WeeklyForecast.toWeatherEntity(): WeatherEntity {
    val cityName = city?.name ?: "Unknown"
    val cityCountry = city?.country ?: "Unknown"
    val coord = city?.coord ?: Coord(0.0, 0.0)

    return WeatherEntity(
        id = cityName.combineWithCountry(cityCountry),
        latitude = coord.lat,
        longitude = coord.lon,
        city = cityName,
        country = cityCountry,
        weatherDailyList = forecastList?.map { it.toWeatherBasic() } ?: emptyList(),
        weatherHourlyList = null,
        weatherCurrent = null
    )
}

fun WeeklyForecastItem.toWeatherBasic(): WeatherBasic {
    return WeatherBasic(
        dateTime = dt,
        currentTemperature = temp?.day ?: 0.0,
        maxTemperature = temp?.max ?: 0.0,
        minTemperature = temp?.min ?: 0.0,
        iconWeather = weather?.firstOrNull()?.iconWeather,
        weatherDescription = weather?.firstOrNull()?.description,
        humidity = humidity,
        percentCloud = clouds,
        windSpeed = speed
    )
}