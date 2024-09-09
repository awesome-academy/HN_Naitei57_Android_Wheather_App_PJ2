package com.sun.weather.data.repository.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sun.weather.data.model.entity.WeatherEntity
import com.sun.weather.data.repository.source.local.converter.WeatherBasicConverter
import com.sun.weather.data.repository.source.local.converter.WeatherBasicListConverter
import com.sun.weather.data.repository.source.local.dao.WeatherDao

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(WeatherBasicConverter::class, WeatherBasicListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        private const val DB_NAME = "Weather.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME,
            ).build()
    }
}
