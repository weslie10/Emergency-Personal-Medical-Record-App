package com.capstone.personalmedicalrecord.ui.patient.data.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notes Fragment"
    }
    val text: LiveData<String> = _text

    private val _state = MutableLiveData<String>()
    fun setState(state: String) {
        _state.value = state
    }
    fun getState(): LiveData<String> = _state

    private var _id = 0
    fun setId(id: Int) {
        _id = id
    }
    fun getId(): Int = _id
}