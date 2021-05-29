package com.example.organizerapp.db.repositories

import androidx.lifecycle.LiveData
import com.example.organizerapp.db.dao.MyListsDao
import com.example.organizerapp.db.entities.MyList

class MyListsRepository(private val myListsDao: MyListsDao) {

    val readAllData: LiveData<List<MyList>> = myListsDao.readAllData()

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