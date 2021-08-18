package com.example.testnav.model.QuickBlox

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.testnav.model.Preferences.SharedPreferences
import com.example.testnav.model.Preferences.SharedPreferences.RemoveUser
import com.example.testnav.model.RegisterUser
import com.example.testnav.model.User
import com.example.testnav.model.Utils.CloseSession
import com.example.testnav.model.Utils.OpenMainActivity
import com.example.testnav.view.MainActivity
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSessionManager
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser

class Authentication {

    private lateinit var registerUser: RegisterUser


    fun SignIn(user: QBUser, context: Context, activity: Activity){
        val qbUser = QBUser()
        //qbUser.id = user.id
        qbUser.login = user.login
        qbUser.password = user.password

        QBUsers.signIn(qbUser).performAsync(object : QBEntityCallback<QBUser> {
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
                SharedPreferences.SaveUser(user)
                loginToChat(user, context, activity)

            }

            override fun onError(error: QBResponseException?) {
                //Toast.makeText(this@MainActivity2, "Login Fail", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Login Fail ${error!!.message}")
            }
        })
    }


    private fun loginToChat(user: QBUser, context: Context, activity: Activity){

        QBChatService.getInstance().login(user, object : QBEntityCallback<Void> {
            override fun onSuccess(v: Void?, b: Bundle?) {
                Log.e(ContentValues.TAG, "Login to Chat Good")

                OpenMainActivity(context, activity)

            }
            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "Login to Chat: ${e!!.message}")

                if(e.message.equals("You have already logged in chat")){
                    OpenMainActivity(context, activity)
                }
            }
        })
    }

    fun RegisterUser(user: QBUser, mUser: User, context: Context, activity: Activity){
        registerUser = RegisterUser()

        QBUsers.signUp(user).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(qbUser: QBUser?, bundle: Bundle?) {
                Log.e(ContentValues.TAG, "Register: ${qbUser}")
                qbUser!!.password = user.password
                mUser.Id = qbUser.id.toString()
                registerUser.SaveUser(mUser)
                SharedPreferences.SaveUser(qbUser)
                SignIn(qbUser!!, context, activity)

            }

            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "Register: ${e!!.message}")
            }
        })
    }

    fun Logout(context: Context){
        QBUsers.signOut().performAsync(object : QBEntityCallback<Void> {
            override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                RemoveUser()
                CloseSession(context)
            }

            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "LogOut: ${e!!.message}")
            }
        })
    }
}

fun DeleteSession(context: Context){
    QBAuth.deleteSession().performAsync(object : QBEntityCallback<Void> {
        override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
            RemoveUser()
            CloseSession(context)
        }

        override fun onError(e: QBResponseException?) {
            Log.e(ContentValues.TAG, "Delete Session Error: ${e!!.message}")
        }
    })
}

object Autentication{
    private var qbChatService: QBChatService = QBChatService.getInstance()

    fun Relogin(user: QBUser, callback: QBEntityCallback<Void>){
        if (!qbChatService.isLoggedIn) {
            qbChatService.login(user, callback)
        } else {
            callback.onSuccess(null, null)
        }
    }
}