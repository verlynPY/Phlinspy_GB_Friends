package com.example.testnav.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.model.Geofences.GeofenceMaps
import com.example.testnav.model.RegisterUser
import com.example.testnav.model.User
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel: ViewModel() {

    private lateinit var registerUser: RegisterUser
    private lateinit var geofenceMaps: GeofenceMaps
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

}