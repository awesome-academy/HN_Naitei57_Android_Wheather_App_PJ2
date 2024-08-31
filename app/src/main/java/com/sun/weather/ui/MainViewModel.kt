package com.sun.weather.ui

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _locationData = MutableLiveData<Pair<Double, Double>>()
    val locationData: LiveData<Pair<Double, Double>> get() = _locationData

    fun requestLocationAndFetchWeather(activity: Activity) {
        val context = getApplication<Application>().applicationContext
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                REQUEST_CODE,
            )
        } else {
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            val locationTask: Task<Location> = fusedLocationProviderClient.lastLocation
            locationTask.addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    _locationData.value = Pair(latitude, longitude)
                } else {
                    Toast.makeText(context, "Location is null", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 1000
    }
}
