package com.example.testnav.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testnav.Utils.PathFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseData {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    fun getLocationUsers(): MutableLiveData<User>{
        val liveData = MutableLiveData<User>()
        databaseReference = FirebaseDatabase.getInstance().getReference(PathFirebase)
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snapshot in snapshot.children){
                    var user = snapshot.getValue(User::class.java)
                    liveData.value = user!!
                    Log.e(TAG, "Heyyyyyyyyyyyyyyyyyy   $user")

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Heyyy  $error")
            }
        })

        return liveData
    }

}