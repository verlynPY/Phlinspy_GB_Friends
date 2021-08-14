package com.example.testnav

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.testnav.model.RegisterUser
import com.example.testnav.model.SettingFilter
import com.example.testnav.model.User
import com.example.testnav.view.MainActivity
import com.example.testnav.view.buttomModifier
import com.example.testnav.viewmodel.MainViewModel
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.quickblox.users.model.QBUser


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

        val settingFilter = SettingFilter("oxGvYueyE4hflxgkEJEH9YBuLFf1", 25, 25,
            8F, "Mujer")

        //lifecycleScope.launchWhenCreated {
            viewModel.SetPreferences(this@RegisterActivity, settingFilter)
        //}

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
        setContent {
            MaterialTheme(
                    colors = if (isSystemInDarkTheme())
                        MaterialThemee.darkColor else MaterialThemee.lightColor
            ) {
                Surface(color = Color(0, 0, 0)) {
                    Register(applicationContext)
                }
            }
        }

    }


    @Composable
    fun Register(context: Context) {
        Column() {
            Row() {
                IconButton(onClick = {
                    finish()
                }) {
                    Icon(vectorResource(R.drawable.ic_back), tint = MaterialThemee.yellow_Mostash)
                }
                Image(
                        imageResource(id = R.drawable.googlemaps_gold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(120.dp)
                        .absolutePadding(right = 30.dp, bottom = 10.dp)

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
                    Column(modifier = Modifier.padding(15.dp)) {
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
                                placeholder = { androidx.compose.material.Text(text = "UserName") },

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Box(modifier = Modifier.preferredWidth(180.dp)
                                .absolutePadding(right = 5.dp)) {
                                OutlinedTextField(value = numberPhone.value,
                                    onValueChange = { numberPhone.value = it },
                                    inactiveColor = MaterialTheme.colors.onSecondary,
                                    activeColor = MaterialTheme.colors.onSecondary,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),

                                    placeholder = { androidx.compose.material.Text(text = "Phone") }
                                )
                            }
                            Box(modifier = Modifier.preferredWidth(180.dp)
                                .absolutePadding(left = 5.dp)) {
                                OutlinedTextField(value = age.value,
                                    onValueChange = { age.value = it },
                                    inactiveColor = MaterialTheme.colors.onSecondary,
                                    activeColor = MaterialTheme.colors.onSecondary,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),

                                    placeholder = { androidx.compose.material.Text(text = "Age") }
                                )
                            }
                        }
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

                        Spacer(modifier = Modifier.height(5.dp))
                        Button(
                                onClick = {
                                    if (latitud!!.equals(0) || longitud!!.equals(0)) {
                                        println("Location cannot be finded")
                                    } else {
                                        var user = User(
                                                "12389423",
                                                userName.value,
                                                numberPhone.value.toLong(),
                                                age.value.toLong(),
                                                hobby.value,
                                                gender,
                                                latitud!!.toString(), longitud!!.toString(),
                                                email.value,
                                                passWord.value
                                        )

                                        //registerUser = RegisterUser()
                                        val qbUser = QBUser()
                                        qbUser.fullName = user.UserName
                                        qbUser.email = user.Email
                                        qbUser.password = user.PassWord
                                        qbUser.phone = user.NumberPhone.toString()
                                        qbUser.login = user.UserName
                                        //viewModel.SaveUsers(user)
                                        viewModel.RegisterQuickBlox(qbUser, user, this@RegisterActivity)
                                    }
                                }, colors = ButtonConstants.defaultButtonColors(backgroundColor = MaterialTheme.colors.secondary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .preferredHeight(60.dp)
                                .clip(
                                        RoundedCornerShape(30.dp)
                                )
                        ) {
                            androidx.compose.material.Text(
                                    text = "SingUp",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                color = MaterialThemee.yellow_Mostash
                            )
                        }
                        Spacer(modifier = Modifier.preferredHeight(20.dp))
                        Row(modifier = Modifier.fillMaxWidth().preferredHeight(180.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Button(
                                onClick = {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    context.startActivity(intent)
                                }, colors =
                                ButtonConstants.defaultButtonColors(
                                    backgroundColor = MaterialTheme.colors.secondary
                                ),
                                modifier = buttomModifier
                            ) {
                                Image(
                                    imageResource(id = R.drawable.google),
                                    modifier = Modifier.absolutePadding(right = 8.dp)
                                )

                                androidx.compose.material.Text(
                                    text = "Sign In",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialThemee.yellow_Mostash
                                )
                            }
                            Spacer(modifier = Modifier.preferredWidth(10.dp))

                            Button(
                                onClick = {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    context.startActivity(intent)

                                }, colors =
                                ButtonConstants.defaultButtonColors(
                                    backgroundColor = MaterialTheme.colors.secondary
                                ),
                                modifier = buttomModifier
                            ) {
                                Image(
                                    imageResource(id = R.drawable.facebook),
                                    modifier = Modifier.absolutePadding(right = 8.dp)
                                )

                                androidx.compose.material.Text(
                                    text = "Sign In",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialThemee.yellow_Mostash
                                )
                            }
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



