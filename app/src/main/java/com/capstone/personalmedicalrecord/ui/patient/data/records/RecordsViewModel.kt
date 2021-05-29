package com.capstone.personalmedicalrecord.ui.patient.data.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class RecordsViewModel(private val recordUseCase: RecordUseCase) : ViewModel() {

    val records = recordUseCase.getRecords().asLiveData()
}