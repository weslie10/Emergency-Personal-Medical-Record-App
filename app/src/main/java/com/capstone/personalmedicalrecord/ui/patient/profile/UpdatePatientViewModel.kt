package com.capstone.personalmedicalrecord.ui.patient.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase

class UpdatePatientViewModel(private val patientUseCase: PatientUseCase) : ViewModel() {
    fun getPatient(id: String) = patientUseCase.getPatientDetail(id).asLiveData()
    fun update(patient: Patient) = patientUseCase.updatePatient(patient)
    fun updatePicture(id: String, url: String) = patientUseCase.updatePicturePatient(id, url)
}