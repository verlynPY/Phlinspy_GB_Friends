package com.example.testnav.model.Geofences

import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class GeofenceMaps {

    fun addMarker(latLng: LatLng, mMap: GoogleMap){
        val markerOptions = MarkerOptions().position(latLng)
        mMap.addMarker(markerOptions)
    }

    fun addCircle(latLng: LatLng, Radius: Float, mMap: GoogleMap){
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(Radius.toDouble())
        circleOptions.fillColor(Color.argb(30, 204,0,255))
        circleOptions.strokeWidth(4f)
        mMap.addCircle(circleOptions)
    }

}