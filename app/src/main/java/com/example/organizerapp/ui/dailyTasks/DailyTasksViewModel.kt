package com.example.organizerapp.ui.dailyTasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.dao.DailyTaskDao
import com.example.organizerapp.db.entities.DailyTask
import com.example.organizerapp.db.entities.User
import com.example.organizerapp.db.repositories.DailyTaskRepository
import com.example.organizerapp.db.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DailyTasksViewModel(application: Application): AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is daily tasks Fragment"
    }
    val text: LiveData<String> = _text

    private val readAllData: LiveData<List<DailyTask>>
    private val repository: DailyTaskRepository

    init{
        val dailyTaskDao = AppDatabase.getInstance(application).dailyTaskDao()
        repository = DailyTaskRepository(dailyTaskDao)
        readAllData = repository.readAllData
    }

    fun addDailyTask(dailyTask: DailyTask){
        viewModelScope.launch(Dispatchers.IO){
            repository.addDailyTask(dailyTask)
        }
    }

    fun getDailyTaskById(dailyTaskId : Int): DailyTask {
        return repository.getDailyTaskById(dailyTaskId)
    }

    fun getDailyTasksByUserId(userId : String): List<DailyTask> {
        return repository.getDailyTasksByUserId(userId)
    }

    fun getAllDailyTasks(): List<DailyTask>{
        return repository.getAllDailyTasks()
    }

}