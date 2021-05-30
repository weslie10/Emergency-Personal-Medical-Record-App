package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class PatientInteractor(private val repository: IRepository) : PatientUseCase {
    override fun getPatients() = repository.getPatients()

    override fun getPatient(id: Int): Flow<Patient> = repository.getPatient(id)

    override fun getPatient(email: String): Flow<Patient> = repository.getPatient(email)

    override fun insertPatient(patient: Patient) = repository.insertPatient(patient)

    override fun updatePatient(patient: Patient) = repository.updatePatient(patient)

    override fun updatePicturePatient(id: Int, url: String) =
        repository.updatePicturePatient(id, url)

    override fun deletePatient(patient: Patient) = repository.deletePatient(patient)
}