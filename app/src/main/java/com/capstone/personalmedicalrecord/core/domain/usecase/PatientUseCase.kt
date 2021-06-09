package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientUseCase {
    fun getPatients(): Flow<List<Patient>>
    fun getPatientDetail(id: String): Flow<Resource<Patient>>
    fun getPatient(email: String): Flow<Resource<Patient>>
    suspend fun insertPatient(patient: Patient): String
    fun updatePatient(patient: Patient)
    fun updatePicturePatient(id: String, url: String)
    fun deletePatient(id: String)
}