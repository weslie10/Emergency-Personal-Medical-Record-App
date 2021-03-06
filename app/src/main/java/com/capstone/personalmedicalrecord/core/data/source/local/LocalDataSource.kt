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
    private val staffDao: StaffDao,
) {

    fun getNotes(idPatient: String) = noteDao.getNotes(idPatient)

    fun getNoteDetail(id: String) = noteDao.getNote(id)

    fun insertNote(note: NoteEntity) = noteDao.insertNote(note)

    fun updateNote(note: NoteEntity) = noteDao.updateNote(note)

    fun deleteNote(id: String) = noteDao.deleteNote(id)

    fun getPatients() = patientDao.getPatients()

    fun getPatientDetail(id: String) = patientDao.getPatientDetail(id)

    fun getPatient(email: String) = patientDao.getPatient(email)

    fun getPatient(email: String, password: String) = patientDao.getPatient(email, password)

    fun insertPatient(patient: PatientEntity) = patientDao.insertPatient(patient)

    fun updatePatient(patient: PatientEntity) = patientDao.updatePatient(patient)

    fun updatePicturePatient(id: String, url: String) = patientDao.updatePicturePatient(id, url)

    fun deletePatient(id: String) = patientDao.deletePatient(id)

    fun getRecords(idPatient: String) = recordDao.getRecords(idPatient)

    fun getRecordDetail(id: String) = recordDao.getRecord(id)

    fun insertRecord(record: RecordEntity) = recordDao.insertRecord(record)

    fun updateRecord(record: RecordEntity) = recordDao.updateRecord(record)

    fun deleteRecord(id: String) = recordDao.deleteRecord(id)

    fun getStaffs() = staffDao.getStaffs()

    fun getStaffDetail(id: String) = staffDao.getStaffDetail(id)

    fun getStaff(email: String) = staffDao.getStaff(email)

    fun getStaff(email: String, password: String) = staffDao.getStaff(email, password)

    fun insertStaff(staff: StaffEntity) = staffDao.insertStaff(staff)

    fun updateStaff(staff: StaffEntity) = staffDao.updateStaff(staff)

    fun updatePictureStaff(id: String, url: String) = staffDao.updatePictureStaff(id, url)

    fun deleteStaff(id: String) = staffDao.deleteStaff(id)
}