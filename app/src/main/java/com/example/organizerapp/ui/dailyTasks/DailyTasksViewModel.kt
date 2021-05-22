package com.example.organizerapp.ui.dailyTasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyTasksViewModel : ViewModel() {
    var task1 = Task("task 1")
    var task2 = Task("task 2")
    var task3 = Task("task 3")
    var allTasks = mutableListOf<Task>(task1, task2, task3)
    private val _text = MutableLiveData<String>().apply {
        value = "No tasks yet"
    }
    val text: LiveData<String> = _text

    fun getTasks(): MutableList<Task> {
        return allTasks
    }

    fun setTaskText(postion : Int, text : String){
        allTasks[postion].text = text
    }
}