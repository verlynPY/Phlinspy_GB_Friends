package com.example.testnav.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.testnav.R
import com.example.testnav.model.User
import com.example.testnav.viewmodel.MainViewModel
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

class ManagerDialog {


    private lateinit var auth: FirebaseAuth


    fun DialogProfilePreview(context: Context,gson: Gson, marker: Marker?, view: View, onClick: (View) -> Unit){
        val test = marker!!.title
        val user = gson.fromJson(test, User::class.java)
        auth = FirebaseAuth.getInstance()
        //val currentId: FirebaseUser = auth.currentUser!!
        val bottomSheetDialog = BottomSheetDialog(view.context, R.style.BottomSheetDialogTheme)
        val bottomView = LayoutInflater.from(context)
            .inflate(R.layout.window_bottom_preview, view.findViewById(R.id.windows_previewContainer))
        val textName_Age = bottomView.findViewById<TextView>(R.id.textName_Age)
        textName_Age.text = user.UserName
        val bottomSendRequests = bottomView.findViewById<Button>(R.id.buttonRequest)
        bottomSendRequests.setOnClickListener (onClick)
        bottomSheetDialog.setContentView(bottomView)
        bottomSheetDialog.show()
    }

}