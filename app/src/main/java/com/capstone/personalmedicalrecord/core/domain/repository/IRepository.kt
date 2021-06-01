package com.capstone.personalmedicalrecord.core.domain.repository

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getNotes(idPatient: Int): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note>
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(id: Int)

    fun getPatients(): Flow<List<Patient>>
    fun getPatient(id: Int): Flow<Resource<Patient>>
    fun getPatient(email: String): Flow<Resource<Patient>>
    fun insertPatient(patient: Patient): Int
    fun updatePatient(patient: Patient)
    fun updatePicturePatient(id: Int, url: String)
    fun deletePatient(patient: Patient)

    fun getRecords(idPatient: Int): Flow<List<Record>>
    fun getRecord(id: Int): Flow<Record>
    fun insertRecord(record: Record)
    fun updateRecord(record: Record)
    fun deleteRecord(id: Int)

    fun getStaffs(): Flow<List<Staff>>
    fun getStaff(id: Int): Flow<Resource<Staff>>
    fun getStaff(email: String): Flow<Resource<Staff>>
    fun insertStaff(staff: Staff): Int
    fun updateStaff(staff: Staff)
    fun updatePictureStaff(id: Int, url: String)
    fun deleteStaff(staff: Staff)
}