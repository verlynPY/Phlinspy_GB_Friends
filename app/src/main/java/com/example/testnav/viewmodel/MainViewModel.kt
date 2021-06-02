package com.example.testnav.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.model.FirebaseData
import com.example.testnav.model.Geofences.GeofenceHelper
import com.example.testnav.model.Geofences.GeofenceMaps
import com.example.testnav.model.MessageRequest
import com.example.testnav.model.RegisterUser
import com.example.testnav.model.User
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception


class MainViewModel: ViewModel() {

    private lateinit var registerUser: RegisterUser
    private lateinit var geofenceMaps: GeofenceMaps
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var firebaseData: FirebaseData
    private lateinit var request: MessageRequest


    private val _uiState = MutableStateFlow(RequestUiState.Success(null))
    val uiState: StateFlow<RequestUiState> = _uiState
    val emailError =  mutableStateOf(false)


    fun SaveUsers(user: User){
        registerUser = RegisterUser()

        if(user.UserName.isNullOrEmpty() || user.NumberPhone.equals(0) ||
                user.Age <= 12 ||
                user.Hobby.isNullOrEmpty() ||
                user.Email.isNullOrEmpty() ||
                user.PassWord.isNullOrEmpty()
        ){
            viewModelScope.launch(Dispatchers.IO){
                registerUser.Register(user)
                var result = registerUser.Register(user)
                println("$result")
            }
        }
        else{
            println("There are field empty")
        }
    }

    fun SendMessageRequest(currentUser: String, user: User){
        request = MessageRequest()

                request.SendMessageRequest(currentUser, user)

    }

    /*fun ReceivedMessageRequest(currentUser: String): MutableLiveData<ArrayList<User>>{
        request = MessageRequest()
        return request.ReceivedMessageRequest(currentUser)
    }*/

    fun EmitReceivedRequest(currentUser: String): MutableLiveData<ArrayList<User>>{
        request = MessageRequest()
            return request.ReceivedMessageRequest(currentUser)
        }


    suspend fun GettingMessageRequest(currentUser: String) = flow{
        try{
            request = MessageRequest()
            val ListReceivedRequest = request.ReceivedMessageRequest(currentUser)
            emit(ListReceivedRequest)
        }
        catch(e: Exception){
            println(e)
        }
    }


    fun GetUserInfo(): MutableLiveData<User>{
        firebaseData = FirebaseData()
            return firebaseData.getLocationUsers()
    }

    fun ShowCircle(latLng: LatLng, Radius: Float, mMap: GoogleMap): Boolean{
        geofenceMaps = GeofenceMaps()
        if(latLng.latitude.equals(null) || latLng.longitude.equals(null)){
            return false
        }
        else{
            geofenceMaps.addCircle(latLng, Radius, mMap)
            return true
        }

    }

    fun SetGeofences(context: Context, GEOFENCE_ID: String, latLng: LatLng, Radius: Float){
        geofencingClient = LocationServices.getGeofencingClient(context)
        geofenceHelper = GeofenceHelper(context)

        val geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, Radius, Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT)
        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
        val pendingIntent = geofenceHelper.getPendingIntent()
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener { Log.d(TAG, "onSuccess: Geofence Added...") }
                .addOnFailureListener { e ->
                    val errorMessage = geofenceHelper.getErrorString(e)
                    Log.d(TAG, "onFailure: $errorMessage")
                }
    }

    sealed class RequestUiState {
        data class Success(val request: ArrayList<User>?): RequestUiState()
        data class Error(val exception: Throwable): RequestUiState()
    }

}