package com.capstone.personalmedicalrecord.ui.patient.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.NoteUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.RecordUseCase

class HomeViewModel(
    private val patientUseCase: PatientUseCase,
    private val noteUseCase: NoteUseCase,
    private val recordUseCase: RecordUseCase,
) : ViewModel() {
    fun getPatient(id: String) = patientUseCase.getPatientDetail(id).asLiveData()
    fun getNotes(id: String) = noteUseCase.getNotes(id).asLiveData()
    fun getRecords(id: String) = recordUseCase.getRecords(id).asLiveData()
}