package com.example.organizerapp.db.entities

import androidx.room.*


@Entity(tableName = "my_list",
    indices = arrayOf(Index(value = ["user_id"])),
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )])
data class MyList(
    @PrimaryKey(autoGenerate = true) val mlid : Int,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "list_details") var listDetails: String?,
    @ColumnInfo(name = "user_id") val userId: String?
    )


