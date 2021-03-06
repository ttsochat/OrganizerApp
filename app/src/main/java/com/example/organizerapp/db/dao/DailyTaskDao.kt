package com.example.organizerapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.organizerapp.db.entities.DailyTask

/**
 * DailyTask is Database Access Object who's interface contains
 * all the methods database queries to be executed between the application
 * and SQLite using Room's framework
 */
@Dao
interface DailyTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDailyTask(dailyTask: DailyTask)

    @Update
    suspend fun updateDailyTask(dailyTask: DailyTask)

    @Delete
    suspend fun deleteDailyTask(dailyTask: DailyTask)

    @Query("SELECT * FROM daily_task WHERE user_id IN (:userId)")
    fun getDailyTasksByUserId(userId: String): LiveData<List<DailyTask>>

    @Query("SELECT COUNT(*) as 'dtid', strftime('%d/%m/%Y', date / 1000, 'unixepoch') as 'description' FROM daily_task WHERE status = 'DONE' AND user_id IN (:userId) group by strftime('%d/%m/%Y', date / 1000, 'unixepoch') ORDER BY date DESC")
    fun getCompletedDailyTasks(userId: String): LiveData<List<DailyTask>>

    @Query("SELECT COUNT(*) as 'dtid', strftime('%d/%m/%Y', date / 1000, 'unixepoch') as 'description' FROM daily_task WHERE user_id IN (:userId) group by strftime('%d/%m/%Y', date / 1000, 'unixepoch') ORDER BY date DESC")
    fun getAllDailyTasksStats(userId: String): LiveData<List<DailyTask>>
}