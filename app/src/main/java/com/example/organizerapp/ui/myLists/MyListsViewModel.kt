package com.example.organizerapp.ui.myLists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyListsViewModel : ViewModel() {

    private val listtest = Lists("food", listOf("spaggeti","creap","klpgfgfgfghfhfhfhfhfhfghfgfghfghhfhffhfghfhgfhgfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghhhhhhhhhhhhhhhhhhhhhhh"), 1 )
    private val listtest2 = Lists("Snack", listOf("banana","mushroom","coca cola"), 2 )
    private val listtest3 = Lists("Favorite Movies", listOf("spaggeti","creap","klpgfgfgfghfhfhfhfhfhfghfgfghfghhfhffhfghfhgfhgfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghhhhhhhhhhhhhhhhhhhhhhh"), 3 )
    private val lala = listOf<Lists>(listtest,listtest2,listtest3)
    private var lists = MutableLiveData(lala)


//    private val lists: MutableLiveData<List<Lists>> by lazy {
//        MutableLiveData<List<Lists>>().also {
//            loadLists()
//        }
//
//    }


    //return lists
    fun getLists(): LiveData<List<Lists>>{
        return lists
    }


    private fun loadLists(): LiveData<List<Lists>> {
        println("load lists")
        val oneLists = Lists(
            "Food",
            listOf(
                "spaggeti",
                "creap",
                "klpgfgfgfghfhfhfhfhfhfghfgfghfghhfhffhfghfhgfhgfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghhhhhhhhhhhhhhhhhhhhhhh"
            ),
            1
        )
        val listOfLists = listOf(oneLists)
        return MutableLiveData(listOfLists)
    }

    fun getCurrentListFromId(id: Long): Lists {
        return if(id == listtest.id){
            listtest
        }else {
            listtest2
        }

    }

    fun removeItemFromList(long: Long) {
        lists.value = lists.value?.toMutableList()?.apply {
            println("remove" + listtest.id)

            remove(lala.find{
                it.id == long
            })
        }

    }

}