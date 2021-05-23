package com.example.organizerapp.db.repositories

import androidx.lifecycle.LiveData
import com.example.organizerapp.db.dao.DailyTaskDao
import com.example.organizerapp.db.entities.DailyTask

class DailyTaskRepository(private val dailyTaskDao: DailyTaskDao) {

    val readAllData : LiveData<List<DailyTask>> = dailyTaskDao.readAllData()

    suspend fun addDailyTask(dailyTask: DailyTask){
        dailyTaskDao.addDailyTask(dailyTask)
    }

    fun getDailyTaskById(dailyTaskId : Int): DailyTask {
        return dailyTaskDao.getDailyTaskById(dailyTaskId)
    }

    fun getDailyTasksByUserId(userId : String): List<DailyTask> {
        return dailyTaskDao.getDailyTasksByUserId(userId)
    }

    fun getAllDailyTasks(): List<DailyTask>{
        return dailyTaskDao.getAllDailyTasks()
    }
}