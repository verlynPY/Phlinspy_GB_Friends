package com.example.testnav.model.DAO

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testnav.model.Request


@Database(entities = arrayOf(Request::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun requestDao(): RequestDao

}