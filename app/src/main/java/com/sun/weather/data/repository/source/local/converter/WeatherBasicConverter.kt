package com.sun.weather.data.repository.source.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sun.weather.data.model.WeatherBasic

class WeatherBasicConverter {
    @TypeConverter
    fun fromWeatherBasic(weatherBasic: WeatherBasic?): String? {
        return weatherBasic?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toWeatherBasic(json: String?): WeatherBasic? {
        return json?.let { Gson().fromJson(it, WeatherBasic::class.java) }
    }
}
