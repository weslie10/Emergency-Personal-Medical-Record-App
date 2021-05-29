package com.capstone.personalmedicalrecord.ui.patient.profile

import androidx.lifecycle.ViewModel
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase

class UpdatePatientViewModel(private val patientUseCase: PatientUseCase) : ViewModel() {

    fun update(patient: Patient) = patientUseCase.updatePatient(patient)
}