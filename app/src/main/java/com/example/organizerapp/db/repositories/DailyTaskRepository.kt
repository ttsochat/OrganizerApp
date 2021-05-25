package com.example.organizerapp.db.repositories

import androidx.lifecycle.LiveData
import com.example.organizerapp.db.dao.DailyTaskDao
import com.example.organizerapp.db.entities.DailyTask

class DailyTaskRepository(private val dailyTaskDao: DailyTaskDao) {

    val readAllData : LiveData<List<DailyTask>> = dailyTaskDao.readAllData()

    suspend fun addDailyTask(dailyTask: DailyTask){
        dailyTaskDao.addDailyTask(dailyTask)
    }

    fun getDailyTasksByUserId(userId : String): LiveData<List<DailyTask>> {
        return dailyTaskDao.getDailyTasksByUserId(userId)
    }

    fun getNumberOfDailyTasksByUserId(userId: String): Int {
        return dailyTaskDao.getNumberOfDailyTasksByUserId(userId)
    }

    fun getDailyTaskById(dailyTaskId : Int): DailyTask {
        return dailyTaskDao.getDailyTaskById(dailyTaskId)
    }

    fun getDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return dailyTaskDao.getDailyTaskGroupedByDate(userId)
    }
}