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

    fun SaveUser(user: User){

        /*auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(user.Email, user.PassWord)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){*/
                        //val User: FirebaseUser = auth.currentUser!!
                        //val Id = User.uid

        reference = FirebaseDatabase.getInstance().reference
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap.put("Id", user.Id)
                        hashMap.put("UserName", user.UserName.capitalize())
                        hashMap.put("NumberPhone", user.NumberPhone.toLong())
                        hashMap.put("Age", user.Age.toLong())
                        hashMap.put("Hobby", user.Hobby.capitalize())
                        hashMap.put("Gender", user.Gender)
                        hashMap.put("Latitude", user.Latitude)
                        hashMap.put("Longitude", user.Longitude)
                        hashMap.put("Email", user.Email)
                        hashMap.put("PassWord", user.PassWord)

                        reference.child(PathFirebase).child(user.Id).setValue(hashMap)
                                .addOnCompleteListener { task ->
                                    Log.e(TAG, "$task")
                                }
                                .addOnFailureListener { fail ->
                                    Log.e(TAG, "$fail")
                                }
                    }
                /*}
                .addOnFailureListener { fail ->

                    Log.e(TAG, "$fail")
                }*/



    //}

}