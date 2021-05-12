package com.example.organizerapp.ui.dailyTasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyTasksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is daily tasks Fragment"
    }
    val text: LiveData<String> = _text
}