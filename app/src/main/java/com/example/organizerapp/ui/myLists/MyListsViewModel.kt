package com.example.organizerapp.ui.myLists


import android.app.Application
import androidx.lifecycle.*
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.MyList
import com.example.organizerapp.db.repositories.MyListsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyListsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyListsRepository
    private var myLists= emptyList<MyList>()


    init{
        val myListsDao = AppDatabase.getInstance(application).myListsDao()
        repository = MyListsRepository(myListsDao)
    }


    fun getMyListsByUserId(userId: String): LiveData<List<MyList>>{
        return repository.getMyListsByUserId(userId)
    }


    fun setMyLists(userLists: List<MyList>){
        myLists = userLists
    }



    fun getCurrentListFromId(id: Int): MyList {
        return repository.getMyListById(id)
    }


    fun removeItemFromList(id: Int) {

        for(list in myLists){
            if(list.mlid == id){

                val newList = myLists.toMutableList()
                newList.remove(list)
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteMyList(list)
                }
                break
            }
        }

    }

    fun addItemToList(newList: MyList){

        viewModelScope.launch(Dispatchers.IO) {
            repository.addMyList(newList)
        }
    }

    fun updateList(newTitle: String, newList: String, id: Int, userId: String){

        val updateList = MyList(id,newTitle,newList,userId)
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMyList(updateList)
        }

    }


}



