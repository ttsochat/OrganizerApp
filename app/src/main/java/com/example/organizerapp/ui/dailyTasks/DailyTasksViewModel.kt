package com.example.organizerapp.ui.dailyTasks

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.dao.DailyTaskDao
import com.example.organizerapp.db.entities.DailyTask
import com.example.organizerapp.db.entities.User
import com.example.organizerapp.db.repositories.DailyTaskRepository
import com.example.organizerapp.db.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class DailyTasksViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DailyTaskRepository
    private var dailyTaskList = emptyList<DailyTask>()

    init{
        val dailyTaskDao = AppDatabase.getInstance(application).dailyTaskDao()
        repository = DailyTaskRepository(dailyTaskDao)
    }

    fun addDailyTask(dailyTask: DailyTask){
        viewModelScope.launch(Dispatchers.IO){
            repository.addDailyTask(dailyTask)
        }
    }

    fun setDailyTasksList(list: List<DailyTask>){
        dailyTaskList=list
    }

    fun getDailyTasksByUserId(userId : String): LiveData<List<DailyTask>> {
        return repository.getDailyTasksByUserId(userId)
    }

    fun getNumberOfDailyTasksByUserId(userId: String): Int {
        return repository.getNumberOfDailyTasksByUserId(userId)
    }

    fun getDailyTaskById(dailyTaskId : Int): DailyTask {
        return repository.getDailyTaskById(dailyTaskId)
    }

    fun getSpecificTask(position : Int) : DailyTask {
        return dailyTaskList[position]
    }

    fun setTaskText(position : Int, text : String, userId: String){
        var updatedTask = DailyTask(dailyTaskList[position].dtid,text, dailyTaskList[position].date,dailyTaskList[position].status,1,userId)
        dailyTaskList[position].description = text
        viewModelScope.launch(Dispatchers.IO){
            repository.updateDailyTask(updatedTask)
        }
    }

    fun removeTask(position: Int){
        var list = dailyTaskList.toMutableList()
        list.removeAt(position)
        dailyTaskList = list.toList()
        for(t in dailyTaskList){
            Log.d("Tag",t.description.toString())
        }
    }


//    var task1 = Task("task 1")
//    var task2 = Task("task 2")
//    var task3 = Task("task 3")
//    var allTasks = mutableListOf(task1, task2, task3)
//    var archivedTasks = mutableListOf<Task>()
//    private val _text = MutableLiveData<String>().apply {
//        value = "No tasks yet"
//    }
//    val text: LiveData<String> = _text
//    fun addNewTask(task : Task){
//        allTasks.add(task)
//    }
//    fun addTaskToSpecificPosition(position : Int, task : Task){
//        allTasks.add(position, task)
//    }
//
//    fun getTasks(): MutableList<Task> {
//        return allTasks
//    }
//
//

//
//    fun archiveItem(index: Int){
//        archivedTasks.add(allTasks[index])
//        allTasks.removeAt(index)
//    }
//
//    fun archivedTasksSize(): Int {
//        val howMany = archivedTasks.size
//        return howMany
//    }
//

}