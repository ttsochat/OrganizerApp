package com.example.organizerapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.organizerapp.db.entities.MyList

@Dao
interface MyListsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMyList(newList: MyList)

    @Update
    suspend fun updateMyList(myList: MyList)

    @Delete
    suspend fun deleteMyList(myList: MyList)

    @Query("SELECT * FROM my_list")
    fun readAllData(): LiveData<List<MyList>>

    @Query("SELECT * FROM my_list WHERE user_id IN (:userId)")
    fun getMyListsByUserId(userId: String): LiveData<List<MyList>>

    @Query("SELECT * FROM my_list WHERE mlid = (:myListId)")
    fun getMyListById(myListId: Int): MyList
}