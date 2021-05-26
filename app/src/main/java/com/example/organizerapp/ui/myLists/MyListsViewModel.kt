package com.example.organizerapp.ui.myLists


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class MyListsViewModel : ViewModel() {

    private var lists = MutableLiveData<List<Lists>>()
    var tsingkoslist : MutableList<Lists> = mutableListOf()


    init{
        tsingkoslist.add(Lists("kasfsfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfd","kalamari",1))
        tsingkoslist.add(Lists("kasfsfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfd","kalamari",2))
        tsingkoslist.add(Lists("kasfsfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfd","kalamari",3))
        tsingkoslist.add(Lists("kasfsfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfd","kalamari",4))
        tsingkoslist.add(Lists("kasfsfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfd","kalamari",5))
        tsingkoslist.add(Lists("kasfsfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfd","kalamari",6))
        tsingkoslist.add(Lists("kasfsfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfd","kalamari",7))
        lists.value = tsingkoslist

    }

    fun getLists(): MutableLiveData<List<Lists>> {
       return lists
    }



    fun getCurrentListFromId(id: Int): Lists {
        for(list in tsingkoslist){
            if(list.id == id){
                return list
            }
        }
        return Lists()
    }



    fun removeItemFromList(id: Int) {
        var l = Lists()
        for(list in tsingkoslist){
            if(list.id == id){
                l = list
            }
        }
        tsingkoslist.remove(l)
        lists.value = tsingkoslist
    }

    fun addItemToList(newList: Lists){
        tsingkoslist.add(newList)
        lists.value = tsingkoslist
        println("tsingos size="+tsingkoslist.size + " lists size=" + lists.value)
    }


    fun updateList(newTitle: String, newList: String, id: Int){
       for(list in tsingkoslist){
           if(list.id == id){
               println("inside if")
               list.title = newTitle
               list.list = newList
           }
       }
        lists.value = tsingkoslist
    }




}



