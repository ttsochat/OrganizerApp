package com.example.organizerapp.db.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(tableName = "daily_task",
    indices = arrayOf(Index(value = ["user_id"])),
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onDelete = CASCADE)])
data class DailyTask(
    @PrimaryKey val dtid: Int,
    @ColumnInfo(name = "date") val date: Date?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "tomato_value") val tomatoValue: Int?,
    @ColumnInfo(name = "user_id") val userId: String?
)