package com.example.organizerapp.ui.myLists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyListsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my lists Fragment"
    }
    val text: LiveData<String> = _text
}