package com.example.organizerapp.ui.tomatoStats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TomatoStatsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tomato stats Fragment!"
    }
    val text: LiveData<String> = _text
}