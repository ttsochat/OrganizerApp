package com.example.organizerapp.ui.myLists


import android.app.Application
import androidx.lifecycle.*
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.MyList
import com.example.organizerapp.db.repositories.MyListsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
    MyListViewModel is a view model used to communicate with the database and send the data
    received to MyListsFragment and MyListsEditFragment.
 */
class MyListsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyListsRepository
    private var myLists= emptyList<MyList>()

    /**
        Init block to connect with MyListsRepository via the AppDatabase.
     */
    init{
        val myListsDao = AppDatabase.getInstance(application).myListsDao()
        repository = MyListsRepository(myListsDao)
    }

    /**
        Return Lists of the user with the given user Id.
     */
    fun getMyListsByUserId(userId: String): LiveData<List<MyList>>{
        return repository.getMyListsByUserId(userId)
    }


    fun setMyLists(userLists: List<MyList>){
        myLists = userLists
    }


    /**
        Returns list with the given id.
     */
    fun getCurrentListFromId(id: Int): MyList {
        return repository.getMyListById(id)
    }


    /**
        Removes list with the given id from database and local list.
     */
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

    /**
        Adds given list to database.
     */
    fun addItemToList(newList: MyList){

        viewModelScope.launch(Dispatchers.IO) {
            repository.addMyList(newList)
        }
    }

    /**
        Sends updated data of the given list to the database.
     */
    fun updateList(newTitle: String, newList: String, id: Int, userId: String){

        val updateList = MyList(id,newTitle,newList,userId)
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMyList(updateList)
        }

    }


}



