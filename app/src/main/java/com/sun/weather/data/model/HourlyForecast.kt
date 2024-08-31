package com.sun.weather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.utils.ext.combineWithCountry

data class HourlyForecast(
    @SerializedName("cnt")
    @Expose
    val cnt: Int = 0,
    @SerializedName("list")
    @Expose
    val forecastList: List<HourlyForecastItem>? = null,
    @SerializedName("city")
    @Expose
    val city: City? = null,
)

data class City(
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("coord")
    @Expose
    val coord: Coord? = null,
    @SerializedName("country")
    @Expose
    val country: String? = null,
)

data class HourlyForecastItem(
    @SerializedName("dt")
    @Expose
    val dt: Long = 0,
    @SerializedName("main")
    @Expose
    val main: Main? = null,
    @SerializedName("weather")
    @Expose
    val weather: List<Weather>? = null,
    @SerializedName("clouds")
    @Expose
    val clouds: Clouds? = null,
    @SerializedName("wind")
    @Expose
    val wind: Wind? = null,
    @SerializedName("dt_txt")
    @Expose
    val dtTxt: String? = null,
    val iconWeather: String? = "",
)

fun HourlyForecast.toWeatherEntity(): WeatherEntity {
    val cityName = city?.name ?: "Unknown"
    val cityCountry = city?.country ?: "Unknown"
    val coord = city?.coord ?: Coord(0.0, 0.0)

    return WeatherEntity(
        id = cityName.combineWithCountry(cityCountry),
        latitude = coord.lat,
        longitude = coord.lon,
        city = cityName,
        country = cityCountry,
        weatherCurrent = null,
        weatherHourlyList = forecastList?.map { it.toWeatherBasic() } ?: emptyList(),
        weatherDailyList = null,
    )
}

fun HourlyForecastItem.toWeatherBasic(): WeatherBasic {
    return WeatherBasic(
        dateTime = dt,
        currentTemperature = main?.currentTemperature ?: 0.0,
        maxTemperature = main?.tempMax ?: 0.0,
        minTemperature = main?.tempMin ?: 0.0,
        iconWeather = weather?.firstOrNull()?.iconWeather,
        weatherDescription = weather?.firstOrNull()?.description,
        humidity = main?.humidity ?: 0,
        percentCloud = clouds?.percentCloud ?: 0,
        windSpeed = wind?.windSpeed ?: 0.0,
    )
}
