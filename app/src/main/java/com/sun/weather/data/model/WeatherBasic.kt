package com.sun.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherBasic(
    var dateTime: Long? = 0,
    var currentTemperature: Double? = 0.0,
    var maxTemperature: Double? = 0.0,
    var minTemperature: Double? = 0.0,
    var iconWeather: String? = "",
    var weatherDescription: String? = "",
    var humidity: Int? = 0,
    var percentCloud: Int? =0,
    var windSpeed: Double? = 0.0
) : Parcelable