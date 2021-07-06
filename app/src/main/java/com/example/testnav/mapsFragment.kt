package com.example.testnav

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.testnav.databinding.ActivityMapsBinding
import com.example.testnav.model.Request
import com.example.testnav.model.User
import com.example.testnav.view.ManagerDialog
import com.example.testnav.viewmodel.MainViewModel
import com.example.testnav.viewmodel.RoomViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@Suppress("DEPRECATION")
class mapsFragment : Fragment(), OnMapReadyCallback{

    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var radioMan: RadioButton
    private lateinit var radioWomen: RadioButton
    private lateinit var Distance_Down: Button
    private lateinit var Distance_Up: Button
    private lateinit var Age_Down: Button
    private lateinit var Age_Up: Button
    private lateinit var Distance: TextView
    private lateinit var Age: TextView
    private var MDialog = ManagerDialog()
    private lateinit var auth: FirebaseAuth
    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var binding: ActivityMapsBinding
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: MainViewModel by viewModels()
    private val viewModelRoom: RoomViewModel by viewModels()
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        val mview:View = inflater.inflate(R.layout.fragment_maps, container, false)
        var button = mview?.findViewById<FloatingActionButton>(R.id.radar)
        button!!.setOnClickListener { view ->


            var bottomSheetDialog = BottomSheetDialog(view.context, R.style.BottomSheetDialogTheme)
            var bottomView = LayoutInflater.from(context)
                    .inflate(R.layout.bg_windows_bottom, view.findViewById(R.id.bottomViewContainer))
            Initialize(bottomView)
            var Distance_Longitud:Int = 1
            Distance.text = Distance_Longitud.toString()
            var Longitud:Int = Distance.text.toString().toInt()

            Distance_Up.setOnClickListener {
                if(Distance_Longitud < 20){
                    Distance_Longitud+= 1
                    Distance.text = Distance_Longitud.toString()
                }
            }
            Distance_Down.setOnClickListener {
                if(Distance_Longitud > 1){
                    Distance_Longitud-= 1
                    Distance.text = Distance_Longitud.toString()
                }
            }

            var Age_Years: Int = 16
            Age.text = Age_Years.toString()
            Age_Up.setOnClickListener {
                if(Age_Years < 55){
                    Age_Years+=1
                    Age.text = Age_Years.toString()
                }
            }
            Age_Down.setOnClickListener {
                if(Age_Years > 16){
                    Age_Years-=1
                    Age.text = Age_Years.toString()
                }
            }
            bottomView.findViewById<Button>(R.id.buttonExplorel)!!.setOnClickListener {

                var gender: String? = null
                radioMan = bottomView.findViewById(R.id.Man)
                radioWomen = bottomView.findViewById(R.id.Women)
                if(radioMan.isChecked){
                    Toast.makeText(context, "Bien", Toast.LENGTH_SHORT).show()
                    gender = "Man"
                }
                if(radioWomen.isChecked){
                    Toast.makeText(context, "Bien", Toast.LENGTH_SHORT).show()
                    gender = "Women"
                }
                val sydney = LatLng(18.8759618, -71.7046444)
            }
            bottomSheetDialog.setContentView(bottomView)
            bottomSheetDialog.show()
        }
        return mview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map)
        if(mapView != null){
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            mapsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onMapReady(p0: GoogleMap?) {
       // p0!!.setMinZoomPreference(14.0f)
        p0!!.setMaxZoomPreference(30.0F)

        try {

            when (getResources().getConfiguration().uiMode and Configuration.UI_MODE_NIGHT_MASK){
                Configuration.UI_MODE_NIGHT_YES -> {
                    val success: Boolean = p0.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.style_json_dark))
                    if (!success) {
                        Log.e(TAG, "Style parsing failed.")
                    }
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    val success: Boolean = p0.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.style_json_ligth))
                    if (!success) {
                        Log.e(TAG, "Style parsing failed.")
                    }
                }
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
        //val location = getLocation(context)
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        var sydney = LatLng(18.8759618, -71.7046444)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                try {
                    sydney = LatLng(location!!.latitude, location.longitude)
                    p0.addMarker(
                        MarkerOptions()
                            .position(sydney)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.circle))
                    )
                    viewModel.ShowCircle(sydney, 50F, p0, true)
                    viewModel.ShowCircle(sydney, 500F, p0, false)
                }
                catch(e: Exception){
                    Log.e(TAG, "Location Error: $e")
                }

            }


        viewModel.GetUserInfo().observe(this, Observer { user ->
            listUser.add(user)

            val latLng = LatLng(user.Latitude.toDouble(), user.Longitude.toDouble())
            val jsonString = gson.toJson(user)

            val marker = MarkerOptions()
                    .position(latLng)
                    .title(jsonString)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.locations_friends))

            /*val currentlocation = Location("current")
            currentlocation.latitude = sydney.latitude
            currentlocation.longitude = sydney.longitude

            val userlocation = Location("user")
            userlocation.latitude = user.Latitude.toDouble()
            userlocation.longitude = user.Longitude.toDouble()

            val distance = currentlocation.distanceTo(userlocation)
            println("Distanceeeeeeee $distance")*/


            p0.addMarker(marker)
            //p0.addCircle(cOptions)
            onClickMarker(p0, user)
        })

        p0.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val button = view?.findViewById<FloatingActionButton>(R.id.current_location)
        button!!.setOnClickListener { view ->
            p0.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
            p0.animateCamera(CameraUpdateFactory.zoomIn(), 2000, null)
        }
    }

    private fun onClickMarker(p0: GoogleMap, user: User){
        p0.setOnMarkerClickListener { marker ->
            val test = marker!!.title
            auth = FirebaseAuth.getInstance()
            //val currentId: FirebaseUser = auth.currentUser!!

            val user = gson.fromJson(test, User::class.java)
            view?.let {
                context?.let { it1 ->
                    MDialog.DialogProfilePreview(it1, gson, marker, it,
                        {
                            //viewModel.SendMessageRequest("oxGvYueyE4hflxgkEJEH9YBuLFf1", user, it1, it)
                            val request = Request(null, "oxGvYueyE4hflxgkEJEH9YBuLFf1", user.Id,
                            user.UserName, false)
                            viewModelRoom.AddRequest(request, it1,it)
                        })


                }
            }

            /*val test = marker!!.title

                            var bottomSheetDialog = view?.let { BottomSheetDialog(it.context, R.style.BottomSheetDialogTheme) }
                            var bottomView = LayoutInflater.from(context)
                                    .inflate(R.layout.window_bottom_preview, view?.findViewById(R.id.windows_previewContainer))
                            var textName_Age = bottomView.findViewById<TextView>(R.id.textName_Age)
                            textName_Age.text = user.UserName
                            var bottomSendRequests = bottomView.findViewById<Button>(R.id.buttonRequest)
                            bottomSendRequests.setOnClickListener {
                                viewModel.SendMessageRequest(currentId.uid, user)
                            }
                            bottomSheetDialog!!.setContentView(bottomView)
                            bottomSheetDialog.show()*/

            true
        }
    }

    private fun Initialize(bottomView: View){
        Distance_Down = bottomView.findViewById(R.id.Distance_Down)
        Distance_Up = bottomView.findViewById(R.id.Distance_Up)
        Age_Down = bottomView.findViewById(R.id.Age_Down)
        Age_Up = bottomView.findViewById(R.id.Age_Up)
        Distance = bottomView.findViewById(R.id.Distance)
        Age = bottomView.findViewById(R.id.Age)
    }


}