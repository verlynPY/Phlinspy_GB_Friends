package com.example.testnav.model.QuickBlox

import com.quickblox.chat.QBChatService

object Connection {

    const val APPLICATION_ID = "91480"
    const val AUTH_KEY = "gJBUXnjUDmYTbuM"
    const val AUTH_SECRET = "SaVESJMkkbB4KDO"
    const val ACCOUNT_KEY = "TXQ7xQFUwCN-y9sZPsM-"

    fun SetConnection() {

        val configurationBuilder = QBChatService.ConfigurationBuilder()
        configurationBuilder.socketTimeout = 300
        configurationBuilder.isUseTls = true
        configurationBuilder.isKeepAlive = true
        configurationBuilder.isAutojoinEnabled = false
        configurationBuilder.setAutoMarkDelivered(true)
        configurationBuilder.isReconnectionAllowed = true
        configurationBuilder.setAllowListenNetwork(true)
        configurationBuilder.port = 5223

        QBChatService.setConfigurationBuilder(configurationBuilder)
    }





}