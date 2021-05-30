package com.example.organizerapp.db.repositories

import androidx.lifecycle.LiveData
import com.example.organizerapp.db.dao.UserDao
import com.example.organizerapp.db.entities.User

/**
 * UserRepository calls all the UserDao functions
 */
class UserRepository(private val userDao: UserDao) {

    val readAllData : LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    fun getUserById(userId : String): User {
        return userDao.getUserById(userId)
    }
}