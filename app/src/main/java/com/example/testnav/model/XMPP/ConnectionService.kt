package com.example.testnav.model.XMPP

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.Nullable

@Suppress("DEPRECATION")
class ConnectionService : Service() {
    private val TAG = "com.example.testnav"
    private var Active: Boolean = false
    private lateinit var mThread: Thread
    private lateinit var mHandler: Handler

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate()");
    }

    private fun Start(){
        Log.d(TAG,"Service started");
        if(!Active){
            Active = true
            if(mThread == null || !mThread.isAlive){
                mThread = Thread(object: Runnable{
                    override fun run() {
                        Looper.prepare()
                        mHandler = Handler()
                        Looper.loop()

                    }
                })
                mThread.start()

            }
        }
    }

    private fun Stop(){
        Log.d(TAG,"Service Stoped");
        Active = false
        mHandler.post(object: Runnable{
            override fun run() {

            }

        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"onStartCommand()");
        Start();
        return START_STICKY;
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy()");
        super.onDestroy()
        Stop()
    }
}