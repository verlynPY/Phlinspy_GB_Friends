package com.example.testnav.viewmodel

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.model.*
import com.example.testnav.model.Geofences.ComponentsMaps
import com.example.testnav.model.Preferences.SharedPreferencesManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class MainViewModel: ViewModel() {

    private lateinit var registerUser: RegisterUser
    private lateinit var componentsMaps: ComponentsMaps
    private lateinit var firebaseData: FirebaseData
    val sharedPreferencesManager = SharedPreferencesManager()

    val liveData = MutableLiveData<SettingFilter>()

    private val _uiState = MutableStateFlow(RequestUiState.Success(null))
    val uiState: StateFlow<RequestUiState> = _uiState
    val emailError =  mutableStateOf(false)

    fun SaveUsers(user: User){
        registerUser = RegisterUser()
        try {
            if (user.UserName.isNullOrEmpty() || user.NumberPhone.equals(0) ||
                user.Age <= 12 ||
                user.Hobby.isNullOrEmpty() ||
                user.Email.isNullOrEmpty() ||
                user.PassWord.isNullOrEmpty()
            ) {
                viewModelScope.launch(Dispatchers.IO) {
                    registerUser.Register(user)
                    var result = registerUser.Register(user)
                    println("$result")
                }
            } else {
                println("There are field empty")
            }
        }
        catch(e: Exception){
            Log.e(TAG, "$e")
        }
    }


    fun SetPreferences(activity: Activity?, settingFilter: SettingFilter){
        viewModelScope.launch(Dispatchers.IO){
            try {
                sharedPreferencesManager.SaveSetting(activity, settingFilter)
            }
            catch (e: Exception){
                Log.e(TAG, "${e}")
            }
        }
    }

    fun GetPreferences(activity: Activity?, UserId: String): MutableLiveData<SettingFilter>{
        viewModelScope.launch(Dispatchers.Main){
             liveData.value = sharedPreferencesManager.ReadSetting(activity, UserId)
        }
        return liveData
    }

    fun GetUserInfo(): MutableLiveData<User>{
        firebaseData = FirebaseData()
            return firebaseData.getLocationUsers()
    }

    fun ShowCircle(latLng: LatLng, Radius: Float, mMap: GoogleMap, CircleShadow: Boolean): Boolean{
        componentsMaps = ComponentsMaps()

            if (latLng.latitude.equals(null) || latLng.longitude.equals(null)) {
                return false
            } else {
                try {
                    if (CircleShadow) {
                        componentsMaps.addCircleShadow(latLng, Radius, mMap)
                    } else {
                        componentsMaps.addCircle(latLng, Radius, mMap)
                    }
                }catch(e: Exception){
                    Log.e(TAG, "$e")
                }

                return true
            }


    }

    sealed class RequestUiState {
        data class Success(val request: ArrayList<User>?): RequestUiState()
        data class Error(val exception: Throwable): RequestUiState()
    }

}