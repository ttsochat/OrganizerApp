package com.example.organizerapp.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.User
import com.example.organizerapp.db.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init{
        val userDao = AppDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun getUserById(userID: String): User? {
        return repository.getUserById(userID)
    }

}