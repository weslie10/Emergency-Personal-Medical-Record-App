package com.capstone.personalmedicalrecord.ui.patient.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase

class UpdatePatientViewModel(private val patientUseCase: PatientUseCase) : ViewModel() {
    fun getPatient(id: Int) = patientUseCase.getPatient(id).asLiveData()
    fun update(patient: Patient) = patientUseCase.updatePatient(patient)
    fun updatePicture(id: Int, url: String) = patientUseCase.updatePicturePatient(id, url)
}