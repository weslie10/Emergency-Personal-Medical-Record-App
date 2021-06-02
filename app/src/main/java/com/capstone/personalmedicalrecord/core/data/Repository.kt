package com.capstone.personalmedicalrecord.core.data

import android.util.Log
import com.capstone.personalmedicalrecord.core.data.source.local.LocalDataSource
import com.capstone.personalmedicalrecord.core.data.source.remote.RemoteDataSource
import com.capstone.personalmedicalrecord.core.data.source.remote.network.ApiResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.PatientResponse
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

    override fun getNotes(idPatient: String): Flow<List<Note>> =
        localDataSource.getNotes(idPatient).map {
            DataMapper.mapNoteEntitiesToDomain(it)
        }

    override fun getNote(id: String): Flow<Note> =
        localDataSource.getNote(id).map {
            if (it != null) DataMapper.mapNoteEntityToDomain(it)
            else it
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

    override fun deleteNote(id: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
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
                    Log.d("getPatientDetail repo", it.toString())
                    if (it != null) {
                        DataMapper.mapPatientEntityToDomain(it)
                    } else it
                }
            }

            override fun shouldFetch(data: Patient?): Boolean {
                return data?.id == null
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

    override suspend fun insertPatient(patient: Patient): String =
        remoteDataSource.insertPatient(DataMapper.mapPatientToResponse(patient))

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

    override fun deletePatient(patient: Patient) {
        val patientEntity = DataMapper.mapPatientToEntity(patient)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.deletePatient(patientEntity)
        }
    }

    override fun getRecords(idPatient: String): Flow<List<Record>> =
        localDataSource.getRecords(idPatient).map {
            DataMapper.mapRecordEntitiesToDomain(it)
        }

    override fun getRecord(id: String): Flow<Record> =
        localDataSource.getRecord(id).map {
            if (it != null) DataMapper.mapRecordEntityToDomain(it)
            else it
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

    override fun deleteRecord(id: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
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
                return data?.id == null
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

    override suspend fun insertStaff(staff: Staff): String =
        remoteDataSource.insertStaff(DataMapper.mapStaffToResponse(staff))

    override fun updateStaff(staff: Staff) {
        val staffEntity = DataMapper.mapStaffToEntity(staff)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            remoteDataSource.updateStaff(DataMapper.mapStaffToResponse(staff))
            localDataSource.updateStaff(staffEntity)
        }
    }

    override fun updatePictureStaff(id: String, url: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
//            remoteDataSource.updatePicturePatient(id, Uri.parse(url))
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