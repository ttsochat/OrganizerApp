package com.example.organizerapp.db.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

/**
 * DailyTask data class that will be the daily_task Table in the Room Database
 * and contains all the DailyTask fields.
 */
@Entity(tableName = "daily_task",
    indices = [Index(value = ["user_id"])],
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onDelete = CASCADE)])
data class DailyTask(
    @PrimaryKey(autoGenerate = true) val dtid: Int,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "date") val date: Date?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "tomato_value") val tomatoValue: Int?,
    @ColumnInfo(name = "user_id") val userId: String?
)