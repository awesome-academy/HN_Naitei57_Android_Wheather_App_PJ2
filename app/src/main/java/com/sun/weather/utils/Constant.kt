package com.sun.weather.utils

import android.content.Context
import android.content.SharedPreferences
import com.sun.weather.BuildConfig

object Constant {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val PRO_URL = "https://pro.openweathermap.org/data/2.5/"
    const val BASE_ICON_URL = "https://openweathermap.org/img/wn/"
    val BASE_API_KEY: String
        get() = BuildConfig.BASE_API_KEY
    const val WEATHER_ENDPOINT = "weather"
    const val WEEKLY_FORECAST_ENDPOINT = "forecast/daily"
    const val HOURLY_FORECAST_ENDPOINT = "forecast/hourly"
    const val APPID_PARAM = "appid"
    const val UNITS_PARAM = "units"
    const val UNITS_VALUE = "metric"
    const val LAT_PARAM = "lat"
    const val LON_PARAM = "lon"
    const val QUERY_PARAM = "q"
    const val CNT_PARAM = "cnt"
    const val LANGUAGE_PARAM = "lang"
    const val FORECAST_DAY = 7
    const val FORECAST_HOUR = 24
    const val BASE_API_SERVICE = "baseApiService"
    const val PRO_API_SERVICE = "proApiService"
    const val BASE_RETROFIT = "baseRetrofit"
    const val PRO_RETROFIT = "proRetrofit"
    const val LANGUAGE_CODE_VIETNAMESE = "vi"
    const val LANGUAGE_CODE_ENGLISH = "en"
    const val DAILY_WORK_MANAGER_ID = "daily_id"
}

object SharedPrefManager {
    private const val SHARE_PREFERENCES_NAME = "SHARE_PREFERENCES"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun getString(
        key: String,
        defaultValue: String?,
    ): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putString(
        key: String,
        value: String,
    ) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getInt(
        key: String,
        defaultValue: Int,
    ): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putInt(
        key: String,
        value: Int,
    ) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }
}
