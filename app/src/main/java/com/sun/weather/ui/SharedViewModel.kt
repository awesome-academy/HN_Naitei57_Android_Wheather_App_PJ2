package com.sun.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var isNetworkAvailable = MutableLiveData<Boolean>()
    private val _selectedLocation = MutableLiveData<String>()
    val selectedLocation: LiveData<String> = _selectedLocation
    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> = _longitude

    fun setLocation(
        lat: Double,
        lon: Double,
    ) {
        _latitude.value = lat
        _longitude.value = lon
    }

    fun checkNetwork(isEnable: Boolean) {
        isNetworkAvailable.postValue(isEnable)
    }

    fun setSelectedLocation(location: String) {
        _selectedLocation.value = location
    }
}
