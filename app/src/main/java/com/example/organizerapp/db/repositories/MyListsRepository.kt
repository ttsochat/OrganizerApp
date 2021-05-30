package com.example.organizerapp.db.repositories

import androidx.lifecycle.LiveData
import com.example.organizerapp.db.dao.MyListsDao
import com.example.organizerapp.db.entities.MyList

/**
   Class MyListsRepository is responsible for communicating with the database in order to store, get
    delete and modify data stored in the database.
 */
class MyListsRepository(private val myListsDao: MyListsDao) {

    suspend fun addMyList(newList: MyList){
        myListsDao.addMyList(newList)
    }

    suspend fun updateMyList(myList: MyList){
        myListsDao.updateMyList(myList)
    }

    suspend fun deleteMyList(myList: MyList){
        myListsDao.deleteMyList(myList)
    }

    fun getMyListsByUserId(userId: String): LiveData<List<MyList>>{
        return myListsDao.getMyListsByUserId(userId)
    }


    fun getMyListById(id: Int): MyList{
        return myListsDao.getMyListById(id)
    }


}