package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientUseCase {
    fun getPatients(): Flow<List<Patient>>
    fun getPatient(id: Int): Flow<Patient>
    fun getPatient(email: String): Flow<Patient>
    fun insertPatient(patient: Patient): Flow<Int>
    fun updatePatient(patient: Patient)
    fun deletePatient(patient: Patient)
}