package com.capstone.personalmedicalrecord.core.data

import android.util.Log
import com.capstone.personalmedicalrecord.core.data.source.local.LocalDataSource
import com.capstone.personalmedicalrecord.core.data.source.remote.RemoteDataSource
import com.capstone.personalmedicalrecord.core.data.source.remote.network.ApiResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.NoteResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.PatientResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.RecordResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.StaffResponse
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
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : IRepository {

    override fun getNotes(idPatient: String): Flow<Resource<List<Note>>> =
        object : NetworkBoundResource<List<Note>, List<NoteResponse>>() {
            override fun loadFromDB(): Flow<List<Note>> {
                return localDataSource.getNotes(idPatient).map {
                    if (it != null) {
                        DataMapper.mapNoteEntitiesToDomain(it)
                    } else {
                        it
                    }
                }
            }

            override fun shouldFetch(data: List<Note>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<NoteResponse>>> =
                remoteDataSource.getNotes(idPatient)

            override suspend fun saveCallResult(data: List<NoteResponse>) {
                val notes = data.map {
                    DataMapper.mapNoteResponseToEntity(it)
                }
                CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                    for (note in notes) {
                        localDataSource.insertNote(note)
                    }
                }
            }
        }.asFlow()

    override fun getNoteDetail(id: String): Flow<Resource<Note>> =
        object : NetworkBoundResource<Note, NoteResponse>() {
            override fun loadFromDB(): Flow<Note> {
                return localDataSource.getNoteDetail(id).map {
                    if (it != null) {
                        Log.d("getNoteDetail repo", it.toString())
                        DataMapper.mapNoteEntityToDomain(it)
                    } else {
                        it
                    }
                }
            }

            override fun shouldFetch(data: Note?): Boolean = data?.id == ""

            override suspend fun createCall(): Flow<ApiResponse<NoteResponse>> =
                remoteDataSource.getNoteDetail(id)

            override suspend fun saveCallResult(data: NoteResponse) {
                val note = DataMapper.mapNoteResponseToEntity(data)
                CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                    localDataSource.insertNote(note)
                }
            }
        }.asFlow()

    override fun insertNote(note: Note) {
        val noteResponse = DataMapper.mapNoteToResponse(note)
        val id = remoteDataSource.insertNote(noteResponse)
        val noteEntity = DataMapper.mapNoteToEntity(note)
        noteEntity.id = id
        localDataSource.insertNote(noteEntity)
    }

    override fun updateNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.updateNote(DataMapper.mapNoteToResponse(note))
            localDataSource.updateNote(noteEntity)
        }
    }

    override fun deleteNote(id: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.deleteNote(id)
            localDataSource.deleteNote(id)
        }
    }

    override fun getPatients(): Flow<List<Patient>> =
        localDataSource.getPatients().map {
            DataMapper.mapPatientEntitiesToDomain(it)
        }

    override fun getPatientDetail(id: String): Flow<Resource<Patient>> =
        object :
            NetworkBoundResource<Patient, PatientResponse>() {
            override fun loadFromDB(): Flow<Patient> {
                return localDataSource.getPatientDetail(id).map {
                    if (it != null) {
                        Log.d("getPatientDetail repo", it.toString())
                        DataMapper.mapPatientEntityToDomain(it)
                    } else Patient()
                }
            }

            override fun shouldFetch(data: Patient?): Boolean {
                return data?.id == ""
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientResponse>> =
                remoteDataSource.getPatientDetail(id)

            override suspend fun saveCallResult(data: PatientResponse) {
                val patientList = DataMapper.mapPatientResponseToEntity(data)
                CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                    localDataSource.insertPatient(patientList)
                }
            }
        }.asFlow()

    override fun getPatient(email: String): Flow<Resource<Patient>> =
        object :
            NetworkBoundResource<Patient, PatientResponse>() {
            override fun loadFromDB(): Flow<Patient> {
                return localDataSource.getPatient(email).map {
                    if (it != null) {
                        DataMapper.mapPatientEntityToDomain(it)
                    } else {
                        Patient()
                    }
                }
            }

            override fun shouldFetch(data: Patient?): Boolean {
                return data?.id == ""
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientResponse>> =
                remoteDataSource.getPatient(email)

            override suspend fun saveCallResult(data: PatientResponse) {
                if (data.id != "") {
                    val patientList = DataMapper.mapPatientResponseToEntity(data)
                    CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                        localDataSource.insertPatient(patientList)
                    }
                } else {
                    Log.d("save", data.toString())
                }
            }
        }.asFlow()

    override suspend fun insertPatient(patient: Patient): String {
        val patientResponse = DataMapper.mapPatientToResponse(patient)
        val id = remoteDataSource.insertPatient(patientResponse)
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        patientEntity.id = id
        localDataSource.insertPatient(patientEntity)
        return id
    }


    override fun updatePatient(patient: Patient) {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.updatePatient(DataMapper.mapPatientToResponse(patient))
            localDataSource.updatePatient(patientEntity)
        }
    }

    override fun updatePicturePatient(id: String, url: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.updatePicturePatient(id, url)
            localDataSource.updatePicturePatient(id, url)
        }
    }

    override fun deletePatient(id: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.deletePatient(id)
            localDataSource.deletePatient(id)
        }
    }

    override fun getRecords(idPatient: String): Flow<Resource<List<Record>>> =
        object : NetworkBoundResource<List<Record>, List<RecordResponse>>() {
            override fun loadFromDB(): Flow<List<Record>> {
                return localDataSource.getRecords(idPatient).map { records ->
                    if (records != null) {
                        DataMapper.mapRecordEntitiesToDomain(records)
                    } else {
                        records
                    }
                }
            }

            override fun shouldFetch(data: List<Record>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<RecordResponse>>> =
                remoteDataSource.getRecords(idPatient)

            override suspend fun saveCallResult(data: List<RecordResponse>) {
                val records = data.map {
                    DataMapper.mapRecordResponseToEntity(it)
                }
                CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                    for (record in records) {
                        localDataSource.insertRecord(record)
                    }
                }
            }
        }.asFlow()

    override fun getRecordDetail(id: String): Flow<Resource<Record>> =
        object : NetworkBoundResource<Record, RecordResponse>() {
            override fun loadFromDB(): Flow<Record> {
                return localDataSource.getRecordDetail(id).map {
                    if (it != null) {
                        Log.d("getRecordDetail repo", it.toString())
                        DataMapper.mapRecordEntityToDomain(it)
                    } else {
                        it
                    }
                }
            }

            override fun shouldFetch(data: Record?): Boolean = data?.id == null

            override suspend fun createCall(): Flow<ApiResponse<RecordResponse>> =
                remoteDataSource.getRecordDetail(id)

            override suspend fun saveCallResult(data: RecordResponse) {
                val record = DataMapper.mapRecordResponseToEntity(data)
                CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                    localDataSource.insertRecord(record)
                }
            }
        }.asFlow()

    override fun insertRecord(record: Record) {
        val recordResponse = DataMapper.mapRecordToResponse(record)
        val id = remoteDataSource.insertRecord(recordResponse)
        val recordEntity = DataMapper.mapRecordToEntity(record)
        recordEntity.id = id
        localDataSource.insertRecord(recordEntity)
    }

    override fun updateRecord(record: Record) {
        val recordEntity = DataMapper.mapRecordToEntity(record)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.updateRecord(DataMapper.mapRecordToResponse(record))
            localDataSource.updateRecord(recordEntity)
        }
    }

    override fun deleteRecord(id: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.deleteRecord(id)
            localDataSource.deleteRecord(id)
        }
    }

    override fun getStaffs(): Flow<List<Staff>> =
        localDataSource.getStaffs().map {
            DataMapper.mapStaffEntitiesToDomain(it)
        }

    override fun getStaffDetail(id: String): Flow<Resource<Staff>> =
        object :
            NetworkBoundResource<Staff, StaffResponse>() {
            override fun loadFromDB(): Flow<Staff> {
                return localDataSource.getStaffDetail(id).map {
                    if (it != null) {
                        DataMapper.mapStaffEntityToDomain(it)
                    } else {
                        Staff()
                    }
                }
            }

            override fun shouldFetch(data: Staff?): Boolean {
                return data?.id == ""
            }

            override suspend fun createCall(): Flow<ApiResponse<StaffResponse>> =
                remoteDataSource.getStaffDetail(id)

            override suspend fun saveCallResult(data: StaffResponse) {
                val staff = DataMapper.mapStaffResponseToEntity(data)
                CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                    localDataSource.insertStaff(staff)
                }
            }
        }.asFlow()

    override fun getStaff(email: String): Flow<Resource<Staff>> =
        object :
            NetworkBoundResource<Staff, StaffResponse>() {
            override fun loadFromDB(): Flow<Staff> {
                return localDataSource.getStaff(email).map {
                    if (it != null) {
                        DataMapper.mapStaffEntityToDomain(it)
                    } else {
                        Staff()
                    }
                }
            }

            override fun shouldFetch(data: Staff?): Boolean {
                return data?.id == ""
            }

            override suspend fun createCall(): Flow<ApiResponse<StaffResponse>> =
                remoteDataSource.getStaff(email)

            override suspend fun saveCallResult(data: StaffResponse) {
                if (data.id != "") {
                    val staff = DataMapper.mapStaffResponseToEntity(data)
                    CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                        localDataSource.insertStaff(staff)
                    }
                } else {
                    Log.d("save", data.toString())
                }
            }
        }.asFlow()

    override suspend fun insertStaff(staff: Staff): String {
        val staffResponse = DataMapper.mapStaffToResponse(staff)
        val id = remoteDataSource.insertStaff(staffResponse)
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        staffEntity.id = id
        localDataSource.insertStaff(staffEntity)
        return id
    }

    override fun updateStaff(staff: Staff) {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.updateStaff(DataMapper.mapStaffToResponse(staff))
            localDataSource.updateStaff(staffEntity)
        }
    }

    override fun updatePictureStaff(id: String, url: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.updatePictureStaff(id, url)
            localDataSource.updatePictureStaff(id, url)
        }
    }

    override fun deleteStaff(id: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.deleteStaff(id)
            localDataSource.deleteStaff(id)
        }
    }
}