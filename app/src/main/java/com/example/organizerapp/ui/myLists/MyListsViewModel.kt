package com.example.organizerapp.ui.myLists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class MyListsViewModel : ViewModel() {

    private val listtest = Lists("food", "spaggeti creap klpgfgfgfghfhfhfhfhfhfghfgfghfghhfhffhfghf" +
            "hgfhgfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghhhhhhhhhhhhhhhhhhhhhhh", 1 )
    private val listtest2 = Lists("Snack", "banana mushroom  coca cola", 2 )
    private val listtest3 = Lists("Favorite Movies", "makaroni xelidoni  sakoyla", 3 )
////   private var lala = listOf<Lists>(listtest,listtest2,listtest3)
////   private var lala : List<Lists> = emptyList()
//    private var lala = mutableListOf(listtest,listtest2,listtest3)
//    private lateinit var lists : MutableLiveData<List<Lists>>
//    private lateinit var localList : MutableList<Lists>

    private val localList = listOf(listtest,listtest2,listtest3)
    private val lists = MutableLiveData(localList)
//    private val lists: MutableLiveData<List<Lists>> by lazy {
//        MutableLiveData<List<Lists>>().also {
//            loadLists()
//        }
//
//    }


    //return lists
    fun getLists(): MutableLiveData<List<Lists>>{
        println("lists size " + lists.value?.size)
        return lists
    }



    fun getCurrentListFromId(id: Long): Lists {

        for(list in localList){
            if(list.id == id){
                return list
            }

        }
        return Lists("","",0)

    }

    fun removeItemFromList(long: Long) {
        localList.toMutableList().remove(localList)
        lists.value = lists.value?.toMutableList()?.apply {

            remove(localList.find{
                it.id == long
            })

        }

    }

    fun addItemToList(newList: Lists){

        val currentList = lists.value
        if(currentList == null){
            lists.postValue(listOf(newList))
        }else {
            val updateList = currentList.toMutableList()
            updateList.add(newList)
            lists.postValue(updateList)
        }
//      lists.value = lists.value?.toMutableList()?.apply {
//
//          this.add(newList)
//      }
        
    }


//    private fun loadLists(): LiveData<List<Lists>> {
//        println("load lists")
//        val oneLists = Lists(
//            "Food",
//            listOf(
//                "spaggeti",
//                "creap",
//                "klpgfgfgfghfhfhfhfhfhfghfgfghfghhfhffhfghfhgfhgfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghfghhhhhhhhhhhhhhhhhhhhhhh"
//            ),
//            1
//        )
//        val listOfLists = listOf(oneLists)
//        return MutableLiveData(listOfLists)
//    }
}



