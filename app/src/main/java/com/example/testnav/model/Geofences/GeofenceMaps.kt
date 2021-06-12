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
        //circleOptions.fillColor(Color.argb(30, 204,0,255))
        circleOptions.strokeWidth(6f)
        circleOptions.strokeColor(Color.argb(100,255,193,7))
        mMap.addCircle(circleOptions)
    }

    fun addCircleShadow(latLng: LatLng, Radius: Float, mMap: GoogleMap){
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(Radius.toDouble())
        circleOptions.fillColor(Color.argb(30,255,193,7))
        circleOptions.strokeWidth(1f)
        circleOptions.strokeColor(Color.argb(10,255,193,7))
        mMap.addCircle(circleOptions)
    }

}