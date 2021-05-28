package com.capstone.personalmedicalrecord.ui.patient.data.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.personalmedicalrecord.core.domain.usecase.NoteUseCase

class NotesViewModel(private val noteUseCase: NoteUseCase) : ViewModel() {
    private val _state = MutableLiveData<String>()
    fun setState(state: String) {
        _state.value = state
    }
    fun getState(): LiveData<String> = _state
}