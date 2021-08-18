package com.example.testnav.view

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testnav.R
import com.example.testnav.mapsFragment
import com.example.testnav.model.Preferences.SharedPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.quickblox.users.model.QBUser

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    private var currentUser:QBUser? = QBUser()
    lateinit var radioMan: RadioButton
    lateinit var radioWomen: RadioButton
    lateinit var Distance_Down: Button
    lateinit var Distance_Up: Button
    lateinit var Age_Down: Button
    lateinit var Age_Up: Button
    lateinit var Distance: TextView
    lateinit var Age: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {

                SharedPreferences.GetCurrentUser()


        }catch(e: Exception){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
            val navController = findNavController(R.id.fragmentContainerView)
            bottomNavigationView.setupWithNavController(navController)

            var bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            var bottomView = LayoutInflater.from(applicationContext)
                .inflate(R.layout.bg_windows_bottom, findViewById(R.id.bottomViewContainer))

            Distance_Down = bottomView.findViewById(R.id.Distance_Down)
            Distance_Up = bottomView.findViewById(R.id.Distance_Up)
            Age_Down = bottomView.findViewById(R.id.Age_Down)
            Age_Up = bottomView.findViewById(R.id.Age_Up)
            Distance = bottomView.findViewById(R.id.Distance)
            Age = bottomView.findViewById(R.id.Age)

            var Distance_Longitud: Int = 1
            Distance.text = Distance_Longitud.toString()
            var Longitud: Int = Distance.text.toString().toInt()

            Distance_Up.setOnClickListener {
                Distance_Longitud += 1
                Distance.text = Distance_Longitud.toString()
            }
            Distance_Down.setOnClickListener {
                Distance_Longitud -= 1
                Distance.text = Distance_Longitud.toString()
            }

            var Age_Years: Int = 16
            Age.text = Age_Years.toString()
            Age_Up.setOnClickListener {
                Age_Years += 1
                Age.text = Age_Years.toString()
            }
            Age_Down.setOnClickListener {
                Age_Years -= 1
                Age.text = Age_Years.toString()
            }

            bottomView.findViewById<Button>(R.id.buttonExplorel)!!.setOnClickListener {

                var gender: String? = null
                radioMan = bottomView.findViewById(R.id.Man)
                radioWomen = bottomView.findViewById(R.id.Women)
                if (radioMan.isChecked) {
                    Toast.makeText(this, "Bien", Toast.LENGTH_SHORT).show()
                    gender = "Man"
                }
                if (radioWomen.isChecked) {
                    Toast.makeText(this, "Bien", Toast.LENGTH_SHORT).show()
                    gender = "Women"
                }

                var bundle = Bundle()
                bundle.putString("gender", gender)
                var mapsfragment = mapsFragment()
                mapsfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mapsFragment, mapsfragment)
                    .commit()
            }
            bottomSheetDialog.setContentView(bottomView)
            //bottomSheetDialog.show()
        }




}