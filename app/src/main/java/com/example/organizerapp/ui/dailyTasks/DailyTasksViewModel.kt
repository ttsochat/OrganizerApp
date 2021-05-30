package com.example.organizerapp.ui.dailyTasks

import android.app.Application
import androidx.lifecycle.*
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.DailyTask
import com.example.organizerapp.db.repositories.DailyTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * DailyTasksViewModel manages the data for daily tasks and displays them to
 * DailyTasksFragment.
 */
class DailyTasksViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DailyTaskRepository
    private var dailyTaskList = emptyList<DailyTask>()


    /**
     * Initialization.
     */
    init{
        val dailyTaskDao = AppDatabase.getInstance(application).dailyTaskDao()
        repository = DailyTaskRepository(dailyTaskDao)
    }

    /**
     * Calls the repository method addDailyTask on a separate thread.
     */
    fun addDailyTask(dailyTask: DailyTask){
        viewModelScope.launch(Dispatchers.IO){
            repository.addDailyTask(dailyTask)
        }
    }

    /**
     * Sets the dailiTaskList of the ViewModel with the list that is given.
     */
    fun setDailyTasksList(list: List<DailyTask>){
        dailyTaskList=list
    }

    /**
     * Calls the repository method getDailyTasksByUserId that returns all the
     * daily tasks of the user.
     */
    fun getDailyTasksByUserId(userId : String): LiveData<List<DailyTask>> {
        return repository.getDailyTasksByUserId(userId)
    }

    /**
     * Returns the daily task of a certain position inside the list.
     */
    fun getSpecificTask(position : Int) : DailyTask {
        return dailyTaskList[position]
    }

    /**
     * Calls the the repository method updateDailyTask on a separate thread
     * that updates the description of a certain daily task.
     */
    fun setTaskText(position : Int, text : String, userId: String){
        val updatedTask = DailyTask(dailyTaskList[position].dtid,text, dailyTaskList[position].date,dailyTaskList[position].status,1,userId)
        dailyTaskList[position].description = text
        viewModelScope.launch(Dispatchers.IO){
            repository.updateDailyTask(updatedTask)
        }
    }

    /**
     * Calls the the repository method updateDailyTask on a separate thread
     * that updates the status of a certain daily task to DONE.
     */
    fun taskDone(position: Int, userId: String) {
        val doneTask = DailyTask(dailyTaskList[position].dtid,dailyTaskList[position].description, dailyTaskList[position].date,"DONE",1,userId)
        val list = dailyTaskList.toMutableList()
        list.removeAt(position)
        dailyTaskList = list.toList()
        viewModelScope.launch(Dispatchers.IO){
            repository.updateDailyTask(doneTask)
        }
    }

    /**
     * Calls the the repository method updateDailyTask on a separate thread
     * that deletes a certain daily task.
     */
    fun deleteTask(position: Int, userId: String) {
        val taskForDeletion = DailyTask(dailyTaskList[position].dtid,dailyTaskList[position].description, dailyTaskList[position].date,dailyTaskList[position].status,1,userId)
        val list = dailyTaskList.toMutableList()
        list.removeAt(position)
        dailyTaskList = list.toList()
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteDailyTask(taskForDeletion)
        }
    }
}