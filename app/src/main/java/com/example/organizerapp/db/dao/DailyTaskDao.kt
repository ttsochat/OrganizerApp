package com.example.organizerapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.organizerapp.db.entities.DailyTask

@Dao
interface DailyTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDailyTask(dailyTask: DailyTask)

    @Query("SELECT * FROM daily_task")
    fun readAllData(): LiveData<List<DailyTask>>

    @Query("SELECT * FROM daily_task")
    fun getAllDailyTasks(): List<DailyTask>

    @Query("SELECT * FROM daily_task WHERE user_id IN (:userId)")
    fun getDailyTasksByUserId(userId: String): List<DailyTask>

    @Query("SELECT * FROM daily_task WHERE dtid = (:dailyTaskId)")
    fun getDailyTaskById(dailyTaskId: Int): DailyTask


}