package com.sun.weather.data.model
import com.google.gson.annotations.SerializedName
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.utils.ext.combineWithCountry

data class WeeklyForecast(
    @SerializedName("city") val city: City,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val forecastList: List<WeeklyForecastItem>,
)

data class WeeklyForecastItem(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temp: Temp,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("speed") val speed: Double,
    @SerializedName("clouds") val clouds: Int,
    val day: String = "",
    val iconWeather: String = "",
)

data class Temp(
    @SerializedName("day") val day: Double,
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val eve: Double,
    @SerializedName("morn") val morn: Double,
)

fun WeeklyForecast.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        id = city.name.combineWithCountry(city.country),
        latitude = city.coord.lat,
        longitude = city.coord.lon,
        city = city.name,
        country = city.country,
        weatherDailyList = forecastList.map { it.toWeatherBasic() },
        weatherHourlyList = null,
        weatherCurrent = null,
    )
}

fun WeeklyForecastItem.toWeatherBasic(): WeatherBasic {
    return WeatherBasic(
        dateTime = dt,
        currentTemperature = temp.day,
        maxTemperature = temp.max,
        minTemperature = temp.min,
        iconWeather = weather.firstOrNull()?.iconWeather,
        humidity = humidity,
        percentCloud = clouds,
        windSpeed = speed,
    )
}
