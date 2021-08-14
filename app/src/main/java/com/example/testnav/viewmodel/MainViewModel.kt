package com.example.testnav.viewmodel

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.model.*
import com.example.testnav.model.Geofences.ComponentsMaps
import com.example.testnav.model.Preferences.SharedPreferences
import com.example.testnav.model.Preferences.SharedPreferencesManager
import com.example.testnav.model.QuickBlox.Authentication
import com.example.testnav.model.QuickBlox.DialogHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.users.model.QBUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class MainViewModel: ViewModel() {

    private lateinit var registerUser: RegisterUser
    private lateinit var componentsMaps: ComponentsMaps
    private lateinit var firebaseData: FirebaseData
    val sharedPreferencesManager = SharedPreferencesManager()
    private val autentication: Authentication = Authentication()


    val liveData = MutableLiveData<SettingFilter>()

    private val _uiState = MutableStateFlow(RequestUiState.Success(null))
    val uiState: StateFlow<RequestUiState> = _uiState
    val emailError =  mutableStateOf(false)

    fun RegisterQuickBlox(qbUser: QBUser, mUser: User, context: Context){
        try{
                if (mUser.UserName.isNullOrEmpty() || mUser.NumberPhone.equals(0) ||
                    mUser.Age <= 12 ||
                    mUser.Hobby.isNullOrEmpty() ||
                    mUser.Email.isNullOrEmpty() ||
                    mUser.PassWord.isNullOrEmpty()
                ) {
            viewModelScope.launch(Dispatchers.Main) {
                autentication.RegisterUser(qbUser, mUser, context)
            } }
        }catch(e: Exception){
            Log.e(TAG, "$e")
        }
    }

    fun LoginQuickBlox(qbUser: QBUser, context: Context){
        try{
            autentication.SignIn(qbUser, context)
        }catch(e: Exception){
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

    fun ReadDialogs(){

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