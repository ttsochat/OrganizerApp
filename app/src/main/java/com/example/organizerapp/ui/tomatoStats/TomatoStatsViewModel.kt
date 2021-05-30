package com.example.organizerapp.ui.tomatoStats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.DailyTask
import com.example.organizerapp.db.repositories.DailyTaskRepository

/**
 * TomatoStatsViewModel manages the user's data for daily tasks and displays the
 * daily statistics to TomatoStatsFragment.
 */
class TomatoStatsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DailyTaskRepository
    private val _text = MutableLiveData<String>().apply {
        value = "Here you can view your daily tomato stats"
    }
    val text: LiveData<String> = _text

    /**
     * Initialization
     */
    init{
        val dailyTaskDao = AppDatabase.getInstance(application).dailyTaskDao()
        repository = DailyTaskRepository(dailyTaskDao)
    }

    /**
     * Calls the repository method to receive the amount of all the completed
     * tasks and groups them by date
     */
    fun getDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return repository.getDailyTaskGroupedByDate(userId)
    }

    /**
     * Calls the repository method to receive the amount of all the uncompleted
     * tasks and groups them by date
     */
    fun getUncompletedDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return repository.getUncompletedDailyTaskGroupedByDate(userId)
    }
}