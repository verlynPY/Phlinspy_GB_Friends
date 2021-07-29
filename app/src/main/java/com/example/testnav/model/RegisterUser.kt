package com.example.testnav.model

import android.content.ContentValues.TAG
import android.util.Log
import com.example.testnav.Utils.PathFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterUser {
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    fun Register(user: User){
        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().reference

        auth.createUserWithEmailAndPassword(user.Email, user.PassWord)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val User: FirebaseUser = auth.currentUser!!
                        val Id = User.uid
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap.put("Id", Id)
                        hashMap.put("UserName", user.UserName.capitalize())
                        hashMap.put("NumberPhone", user.NumberPhone.toString())
                        hashMap.put("Age", user.Age.toString())
                        hashMap.put("Hobby", user.Hobby.capitalize())
                        hashMap.put("Gender", user.Gender)
                        hashMap.put("Latitude", user.Latitude)
                        hashMap.put("Longitude", user.Longitude)
                        hashMap.put("Email", user.Email)
                        hashMap.put("PassWord", user.PassWord)

                        reference.child(PathFirebase).child(Id).setValue(hashMap)
                                .addOnCompleteListener { task ->
                                    Log.e(TAG, "$task")
                                }
                                .addOnFailureListener { fail ->
                                    Log.e(TAG, "$fail")
                                }
                    }
                }
                .addOnFailureListener { fail ->

                    Log.e(TAG, "$fail")
                }



    }

}