package com.example.organizerapp.ui.myLists

import android.icu.util.UniversalTimeScale.toLong
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyListsViewModel : ViewModel() {

    private val listtest = Lists("food", listOf("spaggeti","creap","klpgfgfgfghfhfhfhfhfhfghfgfghfghhfhffhfghfhgfhgfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghhhhhhhhhhhhhhhhhhhhhhh"), 1 )
    private val listtest2 = Lists("Snack", listOf("banana","mushroom","coca cola"), 2 )
    private val listtest3 = Lists("Favorite Movies", listOf("spaggeti","creap","klpgfgfgfghfhfhfhfhfhfghfgfghfghhfhffhfghfhgfhgfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghhhhhhhhhhhhhhhhhhhhhhh"), 1 )
    private val lists = MutableLiveData<List<Lists>>(listOf(listtest,listtest2))


//    private val lists: MutableLiveData<List<Lists>> by lazy {
//        MutableLiveData<List<Lists>>().also {
//            loadLists()
//        }
//
//    }

    fun getLists(): LiveData<List<Lists>>{
        print("getlists" + lists.value?.size)
        return lists //edw epistrefei null
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
        println("listoflists" + listOfLists.size)
        val mutavle = MutableLiveData(listOfLists)
        println("mutavle" + mutavle.value?.size)
        return mutavle
    }

    fun getCurrentListFromId(id: Long): Lists {
        return if(id == listtest.id){
            listtest
        }else {
            listtest2
        }

    }




}