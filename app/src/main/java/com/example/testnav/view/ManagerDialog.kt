package com.example.testnav.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

    fun ConnectionDialog(context: Context){
        val dialog: Dialog = Dialog(context, R.style.BottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.bg_dialog)

        dialog.show()
    }

    fun ToastSuccessfully(context: Context, view:View, message: String){
        val bg = LayoutInflater.from(context)
            .inflate(R.layout.bg_toast_successfuly, view.findViewById(R.id.bg_toast))
        val toast = Toast.makeText(context, "Top", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0,0)
        var textMessage = bg.findViewById<TextView>(R.id.message)
        textMessage.text = message
        toast.view = bg
        toast.show()
    }

}