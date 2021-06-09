package com.capstone.personalmedicalrecord.ui.patient.data.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class RecordAddUpdateViewModel(
    private val recordUseCase: RecordUseCase,
    private val patientUseCase: PatientUseCase,
) : ViewModel() {

    fun insert(record: Record) = recordUseCase.insertRecord(record)

    fun update(record: Record) = recordUseCase.updateRecord(record)

    fun getRecord(id: String) = recordUseCase.getRecordDetail(id).asLiveData()

    fun getPatient(id: String) = patientUseCase.getPatientDetail(id).asLiveData()
}