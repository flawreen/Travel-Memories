package com.example.travelmemories.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Contact Us Fragment"
    }
    val text: LiveData<String> = _text
}