package com.sun.weather.ui.search

import android.app.Application
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class SearchViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private var map: GoogleMap? = null
    private var currentMarker: Marker? = null
    private val _currentMarkerPosition = MutableLiveData<LatLng?>()
    val currentMarkerPosition: LiveData<LatLng?> = _currentMarkerPosition
    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.setOnMarkerClickListener { marker ->
            marker?.let {
                _navigateToHome.value = true
            }
            false
        }
    }

    fun onSearchQuerySubmit(query: String) {
        if (query.isNotEmpty()) {
            val geoCoder = Geocoder(getApplication<Application>().applicationContext)
            try {
                val addressList = geoCoder.getFromLocationName(query, 1)
                val address = addressList?.get(0)
                val latitude = address?.latitude ?: 0.0
                val longitude = address?.longitude ?: 0.0
                _currentMarkerPosition.value = LatLng(latitude, longitude)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateMapMarker(position: LatLng) {
        currentMarker?.remove()
        currentMarker = map?.addMarker(MarkerOptions().position(position).title(position.toString()))
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
    }
}
