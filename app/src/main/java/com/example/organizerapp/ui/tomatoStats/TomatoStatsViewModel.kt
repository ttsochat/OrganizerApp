package com.example.organizerapp.ui.tomatoStats

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.DailyTask
import com.example.organizerapp.db.repositories.DailyTaskRepository

class TomatoStatsViewModel(application: Application) : ViewModel() {

    private val repository: DailyTaskRepository
    private val _text = MutableLiveData<String>().apply {
        value = "This is tomato stats Fragment!"
    }

    init{
        val dailyTaskDao = AppDatabase.getInstance(application).dailyTaskDao()
        repository = DailyTaskRepository(dailyTaskDao)
    }

    val text: LiveData<String> = _text

    fun getDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return repository.getDailyTaskGroupedByDate(userId)
    }
}