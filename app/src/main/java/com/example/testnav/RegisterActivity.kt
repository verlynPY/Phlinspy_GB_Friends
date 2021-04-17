package com.example.testnav

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testnav.model.RegisterUser
import com.example.testnav.model.User
import com.example.testnav.viewmodel.MainViewModel
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialog


@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    val PERMISSION_ID = 42
    val TAG = "com.example.testnav"

    private var locationManager : LocationManager? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by viewModels()
    private lateinit var registerUser: RegisterUser
    private var latitud: Double? = 0.0
    private var longitud: Double? = 0.0
    private var gender: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*var bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        var bottomView = LayoutInflater.from(applicationContext)
            .inflate(R.layout.bg_windows_bottom, findViewById(R.id.bottomViewContainer))
        bottomSheetDialog.setContentView(bottomView)
        bottomSheetDialog.show()*/
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
        setContent {
            MaterialTheme(
                    colors = if (isSystemInDarkTheme())
                        MaterialThemee.darkColor else MaterialThemee.lightColor
            ) {
                Surface(color = Color(0, 0, 0)) {
                    Register()
                }
            }
        }

    }


    @Composable
    fun Register() {
        Column() {
            Row() {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.ArrowBack, tint = Color(150, 150, 150))
                }
                Image(
                        imageResource(id = R.mipmap.imgmaps), modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(140.dp)
                )
            }
            Column(
                    modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp)
            ) {
                Card(
                        modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(topLeft = 100.dp)),
                        backgroundColor = Color(255, 255, 255)
                )
                {
                    Column(modifier = Modifier.padding(20.dp)) {
                        val userName = remember { mutableStateOf("") }
                        val passWord = remember { mutableStateOf("") }
                        val email = remember { mutableStateOf("") }
                        val age = remember { mutableStateOf("") }
                        val numberPhone = remember { mutableStateOf("") }
                        val hobby = remember { mutableStateOf("") }
                        val genderr = remember { mutableStateOf("") }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                        ) {
                            androidx.compose.material.Text(
                                    text = "Register",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    textAlign = TextAlign.Justify
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(value = userName.value,
                                onValueChange = { userName.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { androidx.compose.material.Text(text = "UserName") }

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(value = numberPhone.value,
                                onValueChange = { numberPhone.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { androidx.compose.material.Text(text = "NumberPhone") }

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(value = age.value,
                                onValueChange = { age.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { androidx.compose.material.Text(text = "Age") }

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(value = hobby.value,
                                onValueChange = { hobby.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { androidx.compose.material.Text(text = "Hobby") }

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(value = email.value,
                                onValueChange = { email.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                modifier = Modifier.fillMaxWidth(),
                                isErrorValue = viewModel.emailError.value,
                                placeholder = { androidx.compose.material.Text(text = "Email") }

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(value = passWord.value,
                                onValueChange = { passWord.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                ),
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { androidx.compose.material.Text(text = "PassWord") }

                        )

                        Spacer(modifier = Modifier.height(5.dp))
                        displayRadioGroup()

                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                                onClick = {
                                    if (latitud!!.equals(0) || longitud!!.equals(0)) {
                                        println("Location cannot be finded")
                                    } else {
                                        var user = User(
                                                "",
                                                userName.value,
                                                numberPhone.value.toLong(),
                                                age.value.toInt(),
                                                hobby.value,
                                                gender,
                                                latitud!!, longitud!!,
                                                email.value,
                                                passWord.value
                                        )
                                        registerUser = RegisterUser()
                                        viewModel.SaveUsers(user)
                                    }

                                }, modifier = Modifier
                                .fillMaxWidth()
                                .preferredHeight(55.dp)
                                .clip(
                                        RoundedCornerShape(
                                                topLeft = 25.dp, bottomLeft = 25.dp,
                                                bottomRight = 30.dp
                                        )
                                )
                        ) {
                            androidx.compose.material.Text(
                                    text = "SingUp",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun displayRadioGroup() {
        var selected = remember { mutableStateOf("Male") }
        Box(modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(55.dp)) {
            Row {
                Text(text = "Gender: ")
                RadioButton(
                        selected = selected.value == "Male",
                        onClick = { selected.value = "Male" })
                Text(
                        text = "Male",
                        modifier = Modifier
                                .clickable(onClick = { selected.value = "Male" })
                                .padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                RadioButton(
                        selected = selected.value == "Female",
                        onClick = { selected.value = "Female" })
                Text(
                        text = "Female",
                        modifier = Modifier
                                .clickable(onClick = { selected.value = "Female" })
                                .padding(start = 4.dp)
                )
            }
        }
        gender = selected.value
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        latitud = location.latitude
                        longitud = location.longitude
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()

            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

       fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}



