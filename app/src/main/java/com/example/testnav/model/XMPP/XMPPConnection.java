package com.example.testnav.model.XMPP;

import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class XMPPConnection {

    private String TAG = "XMPPConnection";
    private AbstractXMPPConnection mConnection;
    private ArrayList<MessagesData> mMessagesData = new ArrayList<>();

    public void Connection(){

        new Thread() {
            @Override
            public void run() {


                InetAddress addr = null;
                try {
                    addr = InetAddress.getByName("10.0.0.6");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                HostnameVerifier verifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return false;
                    }
                };
                DomainBareJid serviceName = null;
                try {
                    serviceName = JidCreate.domainBareFrom("localhost");
                } catch (XmppStringprepException e) {
                    e.printStackTrace();
                }
                XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                        .setUsernameAndPassword("user2", "admin")
                        .setPort(5222)
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                        .setXmppDomain(serviceName)
                        .setHostnameVerifier(verifier)
                        .setHostAddress(addr)
                        .setDebuggerEnabled(true)
                        .build();
                mConnection = new XMPPTCPConnection(config);

                try {
                    mConnection.connect();
                    mConnection.login();

                    if(mConnection.isAuthenticated() && mConnection.isConnected()){
                        // Assume we've created an XMPPConnection name "connection"._
                        ChatManager chatManager = ChatManager.getInstanceFor(mConnection);
                        chatManager.addIncomingListener(new IncomingChatMessageListener() {
                            @Override
                            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                                Log.e(TAG,"New message from " + from + ": " + message.getBody());

                                MessagesData data = new MessagesData("received",message.getBody().toString());
                                mMessagesData.add(data);

                                

                            }
                        });

                    }


                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        /*XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setUsernameAndPassword("username", "password");
        try {
            configBuilder.setResource("SomeResource");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        try {
            configBuilder.setXmppDomain("jabber.org");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
// Connect to the server
        connection.connect();
// Log into the server
        connection.login();

...

// Disconnect from the server
        connection.disconnect();*/


    }

}
