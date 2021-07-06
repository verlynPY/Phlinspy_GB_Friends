package com.example.testnav.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Request(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "CurrentUserId") val CurrentUserId: String,
    @ColumnInfo(name = "FriendId") var FriendId: String,
    @ColumnInfo(name = "UserName") var UserName: String,
    @ColumnInfo(name = "StateView") var StateView: Boolean
)
