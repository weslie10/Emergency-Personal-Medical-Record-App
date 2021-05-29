package com.capstone.personalmedicalrecord.ui.patient.data.records

import androidx.lifecycle.ViewModel
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class RecordAddUpdateViewModel(private val recordUseCase: RecordUseCase) : ViewModel() {

    fun insert(record: Record) = recordUseCase.insertRecord(record)

    fun update(record: Record) = recordUseCase.updateRecord(record)
}