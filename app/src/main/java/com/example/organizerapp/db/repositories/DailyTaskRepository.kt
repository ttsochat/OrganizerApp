package com.example.organizerapp.db.repositories

import androidx.lifecycle.LiveData
import com.example.organizerapp.db.dao.DailyTaskDao
import com.example.organizerapp.db.entities.DailyTask

/**
 * DailyTaskRepository calls all the DailyTaskDao functions
 */
class DailyTaskRepository(private val dailyTaskDao: DailyTaskDao) {

    suspend fun addDailyTask(dailyTask: DailyTask){
        dailyTaskDao.addDailyTask(dailyTask)
    }

    suspend fun updateDailyTask(dailyTask: DailyTask){
        dailyTaskDao.updateDailyTask(dailyTask)
    }

    suspend fun deleteDailyTask(dailyTask: DailyTask){
        dailyTaskDao.deleteDailyTask(dailyTask)
    }

    fun getDailyTasksByUserId(userId : String): LiveData<List<DailyTask>> {
        return dailyTaskDao.getDailyTasksByUserId(userId)
    }

    fun getDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return dailyTaskDao.getCompletedDailyTasks(userId)
    }

    fun getUncompletedDailyTaskGroupedByDate(userId: String): LiveData<List<DailyTask>> {
        return dailyTaskDao.getAllDailyTasksStats(userId)
    }
}