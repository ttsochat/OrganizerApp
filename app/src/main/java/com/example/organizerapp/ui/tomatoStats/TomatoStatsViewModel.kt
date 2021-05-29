package com.example.organizerapp.ui.tomatoStats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.DailyTask
import com.example.organizerapp.db.repositories.DailyTaskRepository

class TomatoStatsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DailyTaskRepository
    private val _text = MutableLiveData<String>().apply {
        value = "Here you can view your daily tomato stats"
    }
    val text: LiveData<String> = _text

    init{
        val dailyTaskDao = AppDatabase.getInstance(application).dailyTaskDao()
        repository = DailyTaskRepository(dailyTaskDao)
    }

    fun getDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return repository.getDailyTaskGroupedByDate(userId)
    }

    fun getUncompletedDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return repository.getUncompletedDailyTaskGroupedByDate(userId)
    }
}