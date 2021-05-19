package com.capstone.personalmedicalrecord.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel: ViewModel() {
    private val type = MutableLiveData<String>()

    fun setType(type: String) {
        this.type.value = type
    }
    fun getType(): LiveData<String> = type
}