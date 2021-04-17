package com.example.testnav.model.Geofences

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingRequest
import com.google.type.LatLng

class GeofenceHelper(context: Context?):  ContextWrapper(context){

    private lateinit var pendingIntent: PendingIntent
    fun getGeofencingRequest(geofence: Geofence): GeofencingRequest{
        return GeofencingRequest.Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL)
            .build()
    }

    fun getGeofence(Id: String, latLng: LatLng, Radius: Float, transitionType: Int): Geofence{
        return Geofence.Builder()
            .setCircularRegion(latLng.latitude, latLng.longitude, Radius)
            .setRequestId(Id)
            .setTransitionTypes(transitionType)
            .setLoiteringDelay(5000)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    fun getPendingIntent(): PendingIntent?{
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return  pendingIntent
    }

    fun getErrorString(e: Exception): String{
        if(e is ApiException){
            when (e.statusCode){
                GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> return "GEOFENCE_NOT_AVILABLE"
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> return "GEOFENCE_TOO_MANY_GEOFENCE"
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> return "GEOFENCE_TOO_MANY_PENDING_INTENTS"
            }
        }
        return e.localizedMessage
    }



}