package com.example.testnav.model.Geofences

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "GeofenceBroadcastReceiv"

    override fun onReceive(context: Context?, intent: Intent?) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        val geofencingEvent: GeofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...")
            return
        }
        val geofenceList: List<Geofence> = geofencingEvent.getTriggeringGeofences()
        for (geofence in geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId())
        }
        //        Location location = geofencingEvent.getTriggeringLocation();
        val transitionType: Int = geofencingEvent.getGeofenceTransition()
        when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_SHORT).show()
                context?.let {
                    Toast.makeText(context, "ENTER", Toast.LENGTH_SHORT).show()
                }
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show()
                context?.let {
                    Toast.makeText(context, "DWELL", Toast.LENGTH_SHORT).show()
                }
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show()
                context?.let {
                    Toast.makeText(context, "EXIT", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}