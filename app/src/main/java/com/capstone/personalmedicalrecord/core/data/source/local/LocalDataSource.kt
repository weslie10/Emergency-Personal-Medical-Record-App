package com.capstone.personalmedicalrecord.core.data.source.local

import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.PatientEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.RecordEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.StaffEntity
import com.capstone.personalmedicalrecord.core.data.source.local.room.NoteDao
import com.capstone.personalmedicalrecord.core.data.source.local.room.PatientDao
import com.capstone.personalmedicalrecord.core.data.source.local.room.RecordDao
import com.capstone.personalmedicalrecord.core.data.source.local.room.StaffDao

class LocalDataSource(
    private val noteDao: NoteDao,
    private val patientDao: PatientDao,
    private val recordDao: RecordDao,
    private val staffDao: StaffDao
) {

    fun getNotes() = noteDao.getNotes()

    fun getNote(id: Int) = noteDao.getNote(id)

    fun insertNote(note: NoteEntity) = noteDao.insertNote(note)

    fun updateNote(note: NoteEntity) = noteDao.updateNote(note)

    fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)

    fun getPatients() = patientDao.getPatients()

    fun getPatient(id: Int) = patientDao.getPatient(id)

    fun insertPatient(patient: PatientEntity) = patientDao.insertPatient(patient)

    fun updatePatient(patient: PatientEntity) = patientDao.updatePatient(patient)

    fun deletePatient(patient: PatientEntity) = patientDao.deletePatient(patient)

    fun getRecords() = recordDao.getRecords()

    fun getRecord(id: Int) = recordDao.getRecord(id)

    fun insertRecord(record: RecordEntity) = recordDao.insertRecord(record)

    fun updateRecord(record: RecordEntity) = recordDao.updateRecord(record)

    fun deleteRecord(record: RecordEntity) = recordDao.deleteRecord(record)

    fun getStaffs() = staffDao.getStaffs()

    fun getStaff(id: Int) = staffDao.getStaff(id)

    fun insertStaff(staff: StaffEntity) = staffDao.insertStaff(staff)

    fun updateStaff(staff: StaffEntity) = staffDao.updateStaff(staff)

    fun deleteStaff(staff: StaffEntity) = staffDao.deleteStaff(staff)
}