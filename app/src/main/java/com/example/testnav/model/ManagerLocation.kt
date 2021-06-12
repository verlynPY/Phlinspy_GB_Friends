package com.example.testnav.model

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

object ManagerLocation {


    /*fun RequestPermission(context: Context): Boolean{
        try{
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return true
            }

        }
        catch (e: Exception){
            Log.e(TAG, "$e")
        }
        return false
    }*/


    @SuppressLint("MissingPermission")
    fun getLocation(context: Context): LatLng{
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        var mlocation = LatLng(18.8759618, -71.7046444)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                try {
                    mlocation = LatLng(location!!.latitude, location.latitude)
                }
                catch(e: Exception){
                    Log.e(TAG, "$e")
                }

            }
        return mlocation

    }


}