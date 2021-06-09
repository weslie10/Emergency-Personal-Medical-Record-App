package com.capstone.personalmedicalrecord.ui.patient.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase

class ProfileViewModel(private val patientUseCase: PatientUseCase) : ViewModel() {
    fun getPatient(id: String) = patientUseCase.getPatientDetail(id).asLiveData()
}