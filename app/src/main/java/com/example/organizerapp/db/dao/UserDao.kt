package com.example.organizerapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.organizerapp.db.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE uid = (:userId)")
    fun getUserById(userId : String): User
}