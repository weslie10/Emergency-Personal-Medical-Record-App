package com.capstone.personalmedicalrecord.core.data

import com.capstone.personalmedicalrecord.core.data.source.local.LocalDataSource
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import com.capstone.personalmedicalrecord.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.insertNote(noteEntity)
        }
    }

    override fun updateNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.updateNote(noteEntity)
        }
    }

    override fun deleteNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.deleteNote(noteEntity)
        }
    }

    override fun getPatients(): Flow<List<Patient>> =
        localDataSource.getPatients().map {
            DataMapper.mapPatientEntitiesToDomain(it)
        }

    override fun getPatient(id: Int): Flow<Patient> =
        localDataSource.getPatient(id).map {
            DataMapper.mapPatientEntityToDomain(it)
        }

    override fun getPatient(email: String): Flow<Patient> =
        localDataSource.getPatient(email).map {
            if (it != null) {
                DataMapper.mapPatientEntityToDomain(it)
            } else {
                Patient()
            }
        }

    override fun insertPatient(patient: Patient): Int {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        return localDataSource.insertPatient(patientEntity).toInt()
    }

    override fun updatePatient(patient: Patient) {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.updatePatient(patientEntity)
        }
    }

    override fun updatePicturePatient(id: Int, url: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.updatePicturePatient(id, url)
        }
    }

    override fun deletePatient(patient: Patient) {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.deletePatient(patientEntity)
        }
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
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.insertRecord(recordEntity)
        }
    }

    override fun updateRecord(record: Record) {
        val recordEntity = DataMapper.mapRecordToEntity(record)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.updateRecord(recordEntity)
        }
    }

    override fun deleteRecord(record: Record) {
        val recordEntity = DataMapper.mapRecordToEntity(record)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.deleteRecord(recordEntity)
        }
    }

    override fun getStaffs(): Flow<List<Staff>> =
        localDataSource.getStaffs().map {
            DataMapper.mapStaffEntitiesToDomain(it)
        }

    override fun getStaff(id: Int): Flow<Staff> =
        localDataSource.getStaff(id).map {
            DataMapper.mapStaffEntityToDomain(it)
        }

    override fun getStaff(email: String): Flow<Staff> =
        localDataSource.getStaff(email).map {
            if (it != null) {
                DataMapper.mapStaffEntityToDomain(it)
            } else {
                Staff()
            }
        }

    override fun insertStaff(staff: Staff): Int {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        return localDataSource.insertStaff(staffEntity).toInt()
    }

    override fun updateStaff(staff: Staff) {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.updateStaff(staffEntity)
        }
    }

    override fun updatePictureStaff(id: Int, url: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.updatePictureStaff(id, url)
        }
    }

    override fun deleteStaff(staff: Staff) {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.deleteStaff(staffEntity)
        }
    }
}