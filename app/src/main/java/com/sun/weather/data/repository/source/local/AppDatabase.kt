package com.sun.weather.data.repository.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sun.weather.data.model.FavouriteLocation
import com.sun.weather.data.model.entity.WeatherEntity

import com.sun.weather.data.repository.source.local.converter.WeatherBasicConverter
import com.sun.weather.data.repository.source.local.converter.WeatherBasicListConverter
import com.sun.weather.data.repository.source.local.dao.FavouriteDao
import com.sun.weather.data.repository.source.local.dao.WeatherDao

@Database(entities = [WeatherEntity::class, FavouriteLocation::class], version = 2, exportSchema = false)
@TypeConverters(WeatherBasicConverter::class, WeatherBasicListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun favouriteDao(): FavouriteDao
    companion object {
        private const val DB_NAME = "Weather.db"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).build()
    }
}
