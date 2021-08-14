package com.example.testnav.model.QuickBlox

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.testnav.model.Preferences.SharedPreferences
import com.example.testnav.model.RegisterUser
import com.example.testnav.model.User
import com.example.testnav.view.MainActivity
import com.quickblox.auth.session.QBSessionManager
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser

class Authentication {

    private lateinit var registerUser: RegisterUser

    fun SignIn(user: QBUser, context: Context){
        val qbUser = QBUser()
        qbUser.id = user.id
        qbUser.login = user.login
        qbUser.password = user.password

        QBUsers.signIn(user).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(user: QBUser, args: Bundle?) {
                val sessionParameters = QBSessionManager.getInstance().getSessionParameters()
                sessionParameters.userId
                sessionParameters.userLogin
                sessionParameters.userEmail
                sessionParameters.socialProvider
                sessionParameters.accessToken
                Log.e(ContentValues.TAG, "Id: ${sessionParameters.userId}")
                user.password = qbUser.password
                user.id = qbUser.id
                Log.e(ContentValues.TAG, "Todo Bien SignIn $user")


                loginToChat(user, context)

            }

            override fun onError(error: QBResponseException?) {
                //Toast.makeText(this@MainActivity2, "Login Fail", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Login Fail ${error!!.message}")
            }
        })
    }


    private fun loginToChat(user: QBUser, context: Context){

        //user.password = "verlyn28"
        //user.id = 129522971
        QBChatService.getInstance().login(user, object : QBEntityCallback<Void> {
            override fun onSuccess(v: Void?, b: Bundle?) {
                Log.e(ContentValues.TAG, "Login to Chat Good")

                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)


            }

            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "Login to Chat: ${e!!.message}")
            }
        })

    }

    fun RegisterUser(user: QBUser, mUser: User, context: Context){
        registerUser = RegisterUser()

        QBUsers.signUp(user).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(qbUser: QBUser?, bundle: Bundle?) {
                Log.e(ContentValues.TAG, "Register: ${qbUser}")
                qbUser!!.password = user.password
                mUser.Id = qbUser.id.toString()
                registerUser.SaveUser(mUser)
                SharedPreferences.SaveUser(qbUser)
                SignIn(qbUser!!, context)

            }

            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "Register: ${e!!.message}")
            }
        })
    }

    fun Logout(){
        QBUsers.signOut().performAsync(object : QBEntityCallback<Void> {
            override fun onSuccess(aVoid: Void?, bundle: Bundle?) {

            }

            override fun onError(e: QBResponseException?) {

            }
        })
    }

}