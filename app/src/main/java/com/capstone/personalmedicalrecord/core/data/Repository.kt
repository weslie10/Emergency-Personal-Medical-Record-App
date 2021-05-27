package com.capstone.personalmedicalrecord.core.data

import com.capstone.personalmedicalrecord.core.data.source.local.LocalDataSource
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import com.capstone.personalmedicalrecord.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    private val localDataSource: LocalDataSource
) : IRepository {
    override fun getNotes(): Flow<List<Note>> =
        localDataSource.getNotes().map {
            DataMapper.mapNoteEntitiesToDomain(it)
        }

    override fun getNote(id: Int): Flow<Note> =
        localDataSource.getNote(id).map {
            DataMapper.mapNoteEntityToDomain(it)
        }

    override fun insertNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        localDataSource.insertNote(noteEntity)
    }

    override fun updateNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        localDataSource.updateNote(noteEntity)
    }

    override fun deleteNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        localDataSource.deleteNote(noteEntity)
    }

    override fun getPatients(): Flow<List<Patient>> =
        localDataSource.getPatients().map {
            DataMapper.mapPatientEntitiesToDomain(it)
        }

    override fun getPatient(id: Int): Flow<Patient> =
        localDataSource.getPatient(id).map {
            DataMapper.mapPatientEntityToDomain(it)
        }

    override fun insertPatient(patient: Patient) {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        localDataSource.insertPatient(patientEntity)
    }

    override fun updatePatient(patient: Patient) {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        localDataSource.updatePatient(patientEntity)
    }

    override fun deletePatient(patient: Patient) {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        localDataSource.deletePatient(patientEntity)
    }

    override fun getRecords(): Flow<List<Record>> =
        localDataSource.getRecords().map {
            DataMapper.mapRecordEntitiesToDomain(it)
        }

    override fun getRecord(id: Int): Flow<Record> =
        localDataSource.getRecord(id).map {
            DataMapper.mapRecordEntityToDomain(it)
        }

    override fun insertRecord(record: Record) {
        val recordEntity = DataMapper.mapRecordToEntity(record)
        localDataSource.insertRecord(recordEntity)
    }

    override fun updateRecord(record: Record) {
        val recordEntity = DataMapper.mapRecordToEntity(record)
        localDataSource.updateRecord(recordEntity)
    }

    override fun deleteRecord(record: Record) {
        val recordEntity = DataMapper.mapRecordToEntity(record)
        localDataSource.deleteRecord(recordEntity)
    }

    override fun getStaffs(): Flow<List<Staff>> =
        localDataSource.getStaffs().map {
            DataMapper.mapStaffEntitiesToDomain(it)
        }

    override fun getStaff(id: Int): Flow<Staff> =
        localDataSource.getStaff(id).map {
            DataMapper.mapStaffEntityToDomain(it)
        }

    override fun insertStaff(staff: Staff) {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        localDataSource.insertStaff(staffEntity)
    }

    override fun updateStaff(staff: Staff) {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        localDataSource.updateStaff(staffEntity)
    }

    override fun deleteStaff(staff: Staff) {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        localDataSource.deleteStaff(staffEntity)
    }
}