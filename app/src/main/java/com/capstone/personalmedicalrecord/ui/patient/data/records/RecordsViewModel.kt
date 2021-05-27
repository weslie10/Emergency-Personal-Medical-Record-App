package com.capstone.personalmedicalrecord.ui.patient.data.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class RecordsViewModel(private val recordUseCase: RecordUseCase) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is records Fragment"
    }
    val text: LiveData<String> = _text
}