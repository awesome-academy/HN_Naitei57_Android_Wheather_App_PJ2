@file:Suppress("TooManyFunctions")

package com.example.weather.utils.ext

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.sun.weather.utils.Constant

import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun String?.combineWithCountry(country: String?): String {
    return "$this$country"
}

fun Long.unixTimestampToDateTimeString(): String {
    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("dd MMM - hh:mm a", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault() // user's default time zone
        return outputDateFormat.format(calendar.time)
    } catch (e: IllegalArgumentException) {
        Log.e("Extensions", "Exception occurred", e)
    }

    return this.toString()
}


fun Int.unixTimestampToDateYearString(): String {
    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)
    } catch (e: IllegalArgumentException) {
        Log.e("Extensions", "Exception occurred", e)
    }

    return this.toString()
}

fun Int.unixTimestampToDateMonthString(): String {
    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("MMM, dd", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)
    } catch (e: IllegalArgumentException) {
        Log.e("Extensions", "Exception occurred", e)
    }

    return this.toString()
}

fun Int.unixTimestampToDateString(): String {
    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("dd", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)
    } catch (e: IllegalArgumentException) {
        Log.e("Extensions", "Exception occurred", e)
    }

    return this.toString()
}

fun Int.unixTimestampToHourString(): String {
    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("HH", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)
    } catch (e: IllegalArgumentException) {
        Log.e("Extensions", "Exception occurred", e)
    }

    return this.toString()
}

fun Int.unixTimestampToTimeString(): String {
    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)
    } catch (e: IllegalArgumentException) {
        Log.e("Extensions", "Exception occurred", e)
    }

    return this.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Int.offsetToUTC(): String {
    val zoneOffset = ZoneOffset.ofTotalSeconds(this)
    return "UTC$zoneOffset"
}

