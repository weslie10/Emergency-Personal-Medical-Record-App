package com.capstone.personalmedicalrecord.ui.patient.data.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class RecordAddUpdateViewModel(private val recordUseCase: RecordUseCase) : ViewModel() {

    suspend fun insert(record: Record) = recordUseCase.insertRecord(record)

    fun update(record: Record) = recordUseCase.updateRecord(record)

    fun getRecord(id: String) = recordUseCase.getRecordDetail(id).asLiveData()
}