package com.example.organizerapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "email") val email: String?
)