package com.example.testnav

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.testnav.databinding.ActivityMapsBinding
import com.example.testnav.model.User
import com.example.testnav.view.ManagerDialog
import com.example.testnav.viewmodel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
                Distance_Longitud+= 1
                Distance.text = Distance_Longitud.toString()
            }
            Distance_Down.setOnClickListener {
                Distance_Longitud-= 1
                Distance.text = Distance_Longitud.toString()
            }

            var Age_Years: Int = 16
            Age.text = Age_Years.toString()
            Age_Up.setOnClickListener {
                Age_Years+=1
                Age.text = Age_Years.toString()
            }
            Age_Down.setOnClickListener {
                Age_Years-=1
                Age.text = Age_Years.toString()
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
                viewModel.SetGeofences(view.context, "Nose", sydney, Distance_Longitud.toFloat())
            }
            bottomSheetDialog!!.setContentView(bottomView)
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment mapsFragment.
         */
        // TODO: Rename and change types and number of parameters
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
        val sydney = LatLng(18.8759618, -71.7046444)
        p0!!.addMarker(
                MarkerOptions()
                        .position(sydney)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.circle))
        )
        val cOptions = CircleOptions()
        cOptions.center(sydney)
        cOptions.radius(80.0)
        cOptions.fillColor(Color.argb(30, 100,0,150))
        cOptions.strokeWidth(6f)
        cOptions.strokeColor(Color.argb(30, 100,0,150))

        viewModel.ShowCircle(sydney, 1000F, p0)

        viewModel.GetUserInfo().observe(this, Observer { user ->
            listUser.add(user)

            val latLng = LatLng(user.Latitude.toDouble(), user.Longitude.toDouble())
            var jsonString = gson.toJson(user)

            var marker = MarkerOptions()
                    .position(latLng)
                    .title(jsonString)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.locations_friends))


            p0.addMarker(marker)
            //p0.addCircle(cOptions)

            onClickMarker(p0, user)
        })

        p0.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        var button = view?.findViewById<FloatingActionButton>(R.id.current_location)
        button!!.setOnClickListener { view ->
            p0.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
            p0.animateCamera(CameraUpdateFactory.zoomIn(), 2000, null)
        }
    }

    fun onClickMarker(p0: GoogleMap, user: User){
        p0.setOnMarkerClickListener(object: GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                val test = marker!!.title
                auth = FirebaseAuth.getInstance()
                val currentId: FirebaseUser = auth.currentUser!!
                val user = gson.fromJson(test, User::class.java)
                view?.let {
                    context?.let { it1 ->
                        MDialog.DialogProfilePreview(it1, gson, marker, it,
                            { viewModel.SendMessageRequest(currentId.uid, user) })


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

                    return true
                }


        })
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