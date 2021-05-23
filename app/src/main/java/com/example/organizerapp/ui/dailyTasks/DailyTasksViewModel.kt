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
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DailyTasksViewModel(application: Application): AndroidViewModel(application) {

    var task1 = Task("task 1")
    var task2 = Task("task 2")
    var task3 = Task("task 3")
    var allTasks = mutableListOf<Task>(task1, task2, task3)
    var archivedTasks = mutableListOf<Task>()
//    private val _text = MutableLiveData<String>().apply {
//        value = "No tasks yet"
//    }
//    val text: LiveData<String> = _text
    fun addNewTask(task : Task){
        allTasks.add(task)
    }
    fun addTaskToSpecificPosition(position : Int, task : Task){
        allTasks.add(position, task)
    }

    fun getTasks(): MutableList<Task> {
        return allTasks
    }
    fun getSpecificTask(position : Int) : Task {
        return allTasks[position]
    }

    fun setTaskText(position : Int, text : String){
        allTasks[position].text = text
    }

    fun removeTask(position: Int){
        allTasks.removeAt(position)
    }

    fun archiveItem(index: Int){
        archivedTasks.add(allTasks[index])
        allTasks.removeAt(index)
    }

    fun archivedTasksSize(): Int {
        val howMany = archivedTasks.size
        return howMany
    }

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