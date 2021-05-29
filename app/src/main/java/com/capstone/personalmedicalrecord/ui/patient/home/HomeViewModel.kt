package com.capstone.personalmedicalrecord.ui.patient.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase

class HomeViewModel(private val patientUseCase: PatientUseCase) : ViewModel() {
    fun getPatient(id: Int) = patientUseCase.getPatient(id).asLiveData()
}