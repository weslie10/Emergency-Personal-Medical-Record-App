package com.capstone.personalmedicalrecord.ui.staff.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScannerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is scanner Fragment"
    }
    val text: LiveData<String> = _text
}