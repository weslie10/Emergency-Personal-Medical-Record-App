package com.capstone.personalmedicalrecord.core.domain.repository

import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note>
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)

    fun getPatients(): Flow<List<Patient>>
    fun getPatient(id: Int): Flow<Patient>
    fun insertPatient(patient: Patient)
    fun updatePatient(patient: Patient)
    fun deletePatient(patient: Patient)

    fun getRecords(): Flow<List<Record>>
    fun getRecord(id: Int): Flow<Record>
    fun insertRecord(record: Record)
    fun updateRecord(record: Record)
    fun deleteRecord(record: Record)

    fun getStaffs(): Flow<List<Staff>>
    fun getStaff(id: Int): Flow<Staff>
    fun insertStaff(staff: Staff)
    fun updateStaff(staff: Staff)
    fun deleteStaff(staff: Staff)
}