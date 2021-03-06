package com.example.testnav.model

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.testnav.Utils
import com.example.testnav.Utils.PathRequests
import com.example.testnav.view.ManagerDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.flow.flow

class MessageRequest {

    private lateinit var reference: DatabaseReference
    private var listRequest = ArrayList<Any>()
    private var MDialog = ManagerDialog()


    fun SendMessageRequest(currentUser: String, mUser: User, context: Context, view: View){
        try {
            listRequest.add(mUser)
            reference = FirebaseDatabase.getInstance().reference
            var hashmap: HashMap<String, Any> = HashMap()
            hashmap.put("Id", mUser.Id)
            hashmap.put("UserName", mUser.UserName)
            hashmap.put("Email", mUser.Email)
            hashmap.put("Password", mUser.PassWord)
            hashmap.put("Age", mUser.Age)
            hashmap.put("NumberPhone", mUser.NumberPhone)
            hashmap.put("Longitude", mUser.Longitude)
            hashmap.put("Latitude", mUser.Latitude)
            hashmap.put("Gender", mUser.Gender)
            hashmap.put("Hobby", mUser.Hobby)
            reference.child(PathRequests).child(currentUser).push().setValue(hashmap)
                .addOnCompleteListener { request ->
                    Log.i(TAG, "$request")
                    MDialog.ToastSuccessfully(context, view, "Send Successfuly")

                }
                .addOnFailureListener { fail ->
                    Log.e(TAG, "Error Request $fail")
                    MDialog.ToastSuccessfully(context, view, "Error Request")
                }
        }catch (e: Exception){
            Log.e(TAG,"$e")
        }

    }



}