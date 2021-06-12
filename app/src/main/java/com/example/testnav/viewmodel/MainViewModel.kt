package com.example.testnav.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.util.Log
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.model.*
import com.example.testnav.model.Geofences.GeofenceHelper
import com.example.testnav.model.Geofences.GeofenceMaps
import com.example.testnav.model.ManagerLocation.getLocation
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

    fun SendMessageRequest(currentUser: String, user: User, context: Context, view: View){
        request = MessageRequest()

                request.SendMessageRequest(currentUser, user, context, view)

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

    fun ShowCircle(latLng: LatLng, Radius: Float, mMap: GoogleMap, CircleShadow: Boolean): Boolean{
        geofenceMaps = GeofenceMaps()
        if(latLng.latitude.equals(null) || latLng.longitude.equals(null)){
            return false
        }
        else{
            if(CircleShadow){
                geofenceMaps.addCircleShadow(latLng, Radius, mMap)
            }else{
                geofenceMaps.addCircle(latLng, Radius, mMap)
            }

            return true
        }

    }

    sealed class RequestUiState {
        data class Success(val request: ArrayList<User>?): RequestUiState()
        data class Error(val exception: Throwable): RequestUiState()
    }

}