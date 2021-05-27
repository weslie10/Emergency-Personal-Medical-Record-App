package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientUseCase {
    fun getPatients(): Flow<List<Patient>>
    fun getPatient(id: Int): Flow<Patient>
    fun insertPatient(patient: Patient)
    fun updatePatient(patient: Patient)
    fun deletePatient(patient: Patient)
}