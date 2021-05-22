package com.example.organizerapp.ui.dailyTasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyTasksViewModel : ViewModel() {
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
}