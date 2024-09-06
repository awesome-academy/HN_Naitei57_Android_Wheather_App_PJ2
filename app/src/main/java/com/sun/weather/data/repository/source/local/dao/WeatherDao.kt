package com.sun.weather.data.repository.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.model.entity.WeatherEntry.TBL_WEATHER_NAME

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Transaction
    @Query("SELECT * FROM $TBL_WEATHER_NAME")
    suspend fun getAllData(): List<WeatherEntity>

    @Transaction
    @Query("SELECT * FROM $TBL_WEATHER_NAME WHERE id = :idWeather")
    suspend fun getWeather(idWeather: String): WeatherEntity?

    @Query("DELETE FROM $TBL_WEATHER_NAME WHERE id = :idWeather")
    suspend fun deleteWeather(idWeather: String)
}
