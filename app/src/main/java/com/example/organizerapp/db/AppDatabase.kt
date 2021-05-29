package com.example.organizerapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.organizerapp.db.dao.DailyTaskDao
import com.example.organizerapp.db.dao.MyListsDao
import com.example.organizerapp.db.dao.UserDao
import com.example.organizerapp.db.entities.DailyTask
import com.example.organizerapp.db.entities.MyList
import com.example.organizerapp.db.entities.User


@Database(entities = arrayOf(User::class, DailyTask::class, MyList::class), version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dailyTaskDao() : DailyTaskDao
    abstract fun myListsDao(): MyListsDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "AppDatabase"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}