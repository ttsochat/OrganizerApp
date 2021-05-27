package com.example.organizerapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.organizerapp.db.entities.DailyTask

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

    @Query("SELECT Count(*) FROM daily_task WHERE user_id IN (:userId)")
    fun getNumberOfDailyTasksByUserId(userId: String): Int

    @Query("SELECT * FROM daily_task WHERE dtid = (:dailyTaskId)")
    fun getDailyTaskById(dailyTaskId: Int): DailyTask

}