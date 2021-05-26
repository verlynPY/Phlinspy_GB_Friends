package com.example.testnav.model.XMPP

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import org.jivesoftware.smack.*
import org.jivesoftware.smack.SmackException.NotConnectedException
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import java.io.IOException
import java.net.InetAddress
import javax.net.ssl.HostnameVerifier


@Suppress("DEPRECATION")
class Connection: ConnectionListener {
    private val TAG = "com.example.testnav"

    private var mApplicationContext: Context? = null
    private var mUsername: String? = null
    private var mPassword: String? = null
    private var mServiceName: String? = null
    private var mConnection: XMPPTCPConnection? = null

    enum class ConnectionState {
        CONNECTED, AUTHENTICATED, CONNECTING, DISCONNECTING, DISCONNECTED
    }

    enum class LoggedInState {
        LOGGED_IN, LOGGED_OUT
    }

    private fun Connection(context: Context){
        Log.d(TAG, "Constructor called.");
        mApplicationContext = context.applicationContext
        var jid: String? = PreferenceManager.getDefaultSharedPreferences(mApplicationContext)
            .getString("xmpp_jid", null)
        mPassword = PreferenceManager.getDefaultSharedPreferences(mApplicationContext)
            .getString("xmpp_password", null)

        if(jid != null){
            mUsername = jid.split("@")[0]
            mServiceName = jid.split("@")[1]
        }
        else{
            mUsername = ""
            mServiceName = ""
        }

    }


    fun connect() {

        var configBuilder: XMPPTCPConnectionConfiguration.Builder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setUsernameAndPassword("username", "password")
        configBuilder.setResource("SomeResource")
        configBuilder.setXmppDomain("jabber.org")

        var connection: AbstractXMPPConnection = XMPPTCPConnection(configBuilder.build())
        connection.connect()
// Log into the server
        connection.login()
// Disconnect from the server
        connection.disconnect()
    }

    fun disconnect(){
        Log.d(TAG, "Disconnecting from serser $mServiceName")
        try {
            if (mConnection != null) {
                mConnection!!.disconnect()
            }
        } catch (e: NotConnectedException) {
            var ConnectionState = ConnectionState.DISCONNECTED
            e.printStackTrace()
        }
        mConnection = null
    }



    override fun connected(connection: org.jivesoftware.smack.XMPPConnection?) {
        TODO("Not yet implemented")
    }

    override fun authenticated(connection: org.jivesoftware.smack.XMPPConnection?, resumed: Boolean) {
        TODO("Not yet implemented")
    }

    override fun connectionClosed() {
        var ConnectionState = ConnectionState.DISCONNECTED;
        Log.d(TAG, "Connectionclosed()");
    }

    override fun connectionClosedOnError(e: Exception?) {
        var ConnectionState = ConnectionState.DISCONNECTED;
        Log.d(TAG, "ConnectionClosedOnError, error " + e.toString())
    }

    override fun reconnectionSuccessful() {
        var ConnectionState = ConnectionState.CONNECTED;
        Log.d(TAG, "ReconnectionSuccessful()");
    }

    override fun reconnectingIn(seconds: Int) {
        var ConnectionState = ConnectionState.CONNECTING;
        Log.d(TAG, "ReconnectingIn() ");

    }

    override fun reconnectionFailed(e: Exception?) {
        var ConnectionState = ConnectionState.DISCONNECTED;
        Log.d(TAG, "ReconnectionFailed()");
    }

}