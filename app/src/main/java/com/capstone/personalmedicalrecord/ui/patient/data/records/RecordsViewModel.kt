package com.capstone.personalmedicalrecord.ui.patient.data.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class RecordsViewModel(
    private val recordUseCase: RecordUseCase,
    private val patientUseCase: PatientUseCase
) : ViewModel() {
    fun getRecords(idPatient: Int) = recordUseCase.getRecords(idPatient).asLiveData()

    fun getPatient(id: Int) = patientUseCase.getPatient(id).asLiveData()

    fun update(patient: Patient) = patientUseCase.updatePatient(patient)

    fun getRecord(id: Int) = recordUseCase.getRecord(id).asLiveData()

    fun deleteRecord(id: Int) = recordUseCase.deleteRecord(id)
}