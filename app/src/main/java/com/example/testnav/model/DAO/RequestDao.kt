package com.example.testnav.model.DAO

import androidx.room.*
import com.example.testnav.model.Request
import com.example.testnav.model.SettingFilter
import com.example.testnav.model.Utils.ID

@Dao
interface RequestDao {

    @Query("SELECT * FROM request")
    suspend fun GettAll(): List<Request>

    @Query("SELECT * FROM request WHERE CurrentUserId = :ID")
    suspend fun GetRequestById(ID: String): List<Request>

    @Insert
    suspend fun AddRequest(vararg request: Request)

    @Query("UPDATE request SET StateView = :StatusView WHERE FriendId = :ID")
    suspend fun UpdateStatusView(StatusView: Boolean, ID: String)

    @Delete
    suspend fun Delete(request: Request)

}
