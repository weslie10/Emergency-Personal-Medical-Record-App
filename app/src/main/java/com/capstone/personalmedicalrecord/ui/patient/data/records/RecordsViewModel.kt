package com.capstone.personalmedicalrecord.ui.patient.data.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class RecordsViewModel(
    private val recordUseCase: RecordUseCase,
    private val patientUseCase: PatientUseCase,
) : ViewModel() {
    fun getRecords(idPatient: String) = recordUseCase.getRecords(idPatient).asLiveData()

    fun getPatient(id: String) = patientUseCase.getPatientDetail(id).asLiveData()

    fun update(patient: Patient) = patientUseCase.updatePatient(patient)

    fun getRecordDetail(id: String) = recordUseCase.getRecordDetail(id).asLiveData()

    fun deleteRecord(id: String) = recordUseCase.deleteRecord(id)
}