package com.sun.weather.data.repository.source.remote.api

import com.sun.weather.data.model.CurrentWeather
import com.sun.weather.data.model.HourlyForecast
import com.sun.weather.data.model.WeeklyForecast
import com.sun.weather.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constant.WEATHER_ENDPOINT)
    suspend fun getCurrentWeather(
        @Query(Constant.QUERY_PARAM) city: String,
        @Query(Constant.APPID_PARAM) appid: String,
        @Query(Constant.UNITS_PARAM) units: String,
        @Query(Constant.LANGUAGE_PARAM) lang: String
    ): CurrentWeather

    @GET(Constant.WEATHER_ENDPOINT)
    suspend fun getCurrentLocationWeather(
        @Query(Constant.LAT_PARAM) latitude: Double,
        @Query(Constant.LON_PARAM) longitude: Double,
        @Query(Constant.APPID_PARAM) appid: String,
        @Query(Constant.UNITS_PARAM) units: String,
        @Query(Constant.LANGUAGE_PARAM) lang: String
    ): CurrentWeather

    @GET(Constant.WEEKLY_FORECAST_ENDPOINT)
    suspend fun getWeeklyForecast(
        @Query(Constant.QUERY_PARAM) city: String,
        @Query(Constant.UNITS_PARAM) units: String,
        @Query(Constant.CNT_PARAM) count: Int,
        @Query(Constant.APPID_PARAM) appid: String,
        @Query(Constant.LANGUAGE_PARAM) lang: String
    ): WeeklyForecast

    @GET(Constant.HOURLY_FORECAST_ENDPOINT)
    suspend fun getHourlyForecast(
        @Query(Constant.QUERY_PARAM) city: String,
        @Query(Constant.UNITS_PARAM) units: String,
        @Query(Constant.CNT_PARAM) count: Int,
        @Query(Constant.APPID_PARAM) appid: String,
        @Query(Constant.LANGUAGE_PARAM) lang: String
    ): HourlyForecast
}
