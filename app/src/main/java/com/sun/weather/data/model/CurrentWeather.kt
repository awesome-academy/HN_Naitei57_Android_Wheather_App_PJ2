package com.sun.weather.data.model

import com.example.weather.utils.ext.combineWithCountry
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sun.weather.data.model.entity.WeatherEntity

data class CurrentWeather(
    @SerializedName("main")
    @Expose
    var main: Main? = null,
    @SerializedName("weather")
    @Expose
    var weathers: List<Weather>? = null,
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null,
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null,
    @SerializedName("coord")
    @Expose
    var coord: Coord? = Coord(0.0, 0.0),
    @SerializedName("dt")
    @Expose
    var dt: Long? = null,
    @SerializedName("name")
    @Expose
    var nameCity: String? = null,
    @SerializedName("sys")
    @Expose
    var sys: Sys? = null,
    var day: String? = "",
    var iconWeather: String? = "",
)

data class Main(
    @SerializedName("temp")
    @Expose
    var currentTemperature: Double? = null,
    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null,
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double? = null,
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double? = null,
)

data class Weather(
    @SerializedName("icon")
    @Expose
    var iconWeather: String? = null,
    @SerializedName("main")
    @Expose
    var main: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
)

data class Wind(
    @SerializedName("speed")
    @Expose
    var windSpeed: Double? = null,
)

data class Clouds(
    @SerializedName("all")
    @Expose
    var percentCloud: Int? = null,
)

data class Coord(
    @SerializedName("lon")
    @Expose
    var lon: Double? = null,
    @SerializedName("lat")
    @Expose
    var lat: Double? = null,
)

data class Sys(
    @SerializedName("country")
    @Expose
    var country: String? = null,
)

fun CurrentWeather.toWeatherEntity(): WeatherEntity {
    val country: String = sys?.country ?: "Unknown"
    val id = nameCity?.combineWithCountry(country) ?: "Unknown"
    val latitude = coord?.lat ?: 0.0
    val longitude = coord?.lon ?: 0.0
    val timeZone = dt ?: 0
    val city = nameCity ?: "Unknown"
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
        weatherDailyList = weatherDailyList
    )
}

fun CurrentWeather.toWeatherBasic(): WeatherBasic? {
    return if (main != null && weathers?.isNotEmpty() == true && wind != null && clouds != null) {
        WeatherBasic(
            dateTime = dt ?: 0,
            currentTemperature = main?.currentTemperature ?: 0.0,
            maxTemperature = main?.tempMax ?: 0.0,
            minTemperature = main?.tempMin ?: 0.0,
            iconWeather = weathers?.firstOrNull()?.iconWeather,
            weatherDescription = weathers?.firstOrNull()?.description,
            humidity = main?.humidity ?: 0,
            percentCloud = clouds?.percentCloud ?: 0,
            windSpeed = wind?.windSpeed ?: 0.0
        )
    } else {
        null
    }
}
