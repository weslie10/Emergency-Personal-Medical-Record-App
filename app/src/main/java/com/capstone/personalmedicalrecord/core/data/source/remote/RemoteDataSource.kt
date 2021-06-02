package com.capstone.personalmedicalrecord.core.data.source.remote

import android.util.Log
import com.capstone.personalmedicalrecord.core.data.source.remote.network.ApiResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.NoteResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.PatientResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.RecordResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.StaffResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class RemoteDataSource {
    private val db = FirebaseFirestore.getInstance()

    private val noteDb = db.collection("note")
    private val patientDb = db.collection("patient")
    private val recordDb = db.collection("record")
    private val staffDb = db.collection("staff")

    fun getNotes(idPatient: String): Flow<ApiResponse<List<NoteResponse>>> {
        return flow {
            val data = noteDb.get().await()
            val notes = data.toObjects(NoteResponse::class.java)
            if (notes.isNotEmpty()) {
                val list = notes.filter { it.idPatient == idPatient }
                Log.d("getNotes", list.toString())
                emit(ApiResponse.Success(list))
            } else {
                emit(ApiResponse.Success(arrayListOf<NoteResponse>()))
            }
        }
    }

    fun getNoteDetail(id: String): Flow<ApiResponse<NoteResponse>> {
        return flow {
            val idNote = noteDb.document(id).id
            val data = noteDb.document(id).get().await()
            val note = data.toObject(NoteResponse::class.java) as NoteResponse
            note.id = idNote
            Log.d("getNoteDetail", note.toString())
            emit(ApiResponse.Success(note))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertNote(note: NoteResponse): String {
        return try {
            val result = noteDb.add(note)
                .addOnSuccessListener {
                    Log.d("insertNote", "Saved to DB")
                }
                .addOnFailureListener {
                    Log.d("insertNote", "Error saving to DB")
                }
                .await()
            result.id
        } catch (e: Exception) {
            Log.e("insertNote", e.message.toString())
            ""
        }
    }

    fun getPatient(email: String): Flow<ApiResponse<PatientResponse>> {
        return flow {
            val data = patientDb.get().await()
            val patients = data.toObjects(PatientResponse::class.java)
            if (patients.isNotEmpty()) {
                var isAvailable = true
                var index = 0
                for (patient in patients) {
                    if (patient.email == email) {
                        isAvailable = false
                        patient.id = data.documentChanges[index].document.id
                        Log.d("getPatient", patient.toString())
                        emit(ApiResponse.Success(patient))
                        break
                    }
                    index++
                }
                if (isAvailable) {
                    emit(ApiResponse.Success(PatientResponse()))
                }
            } else {
                emit(ApiResponse.Success(PatientResponse()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPatientDetail(id: String): Flow<ApiResponse<PatientResponse>> {
        return flow {
            val idPatient = patientDb.document(id).id
            val data = patientDb.document(id).get().await()
            val patient = data.toObject(PatientResponse::class.java) as PatientResponse
            patient.id = idPatient
            Log.d("getPatientDetail", patient.toString())
            emit(ApiResponse.Success(patient))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertPatient(patient: PatientResponse): String {
        return try {
            val result = patientDb.add(patient)
                .addOnSuccessListener {
                    Log.d("insertPatient", "Saved to DB")
                }
                .addOnFailureListener {
                    Log.e("insertPatient", "Error saving to DB")
                }
                .await()
            result.id
        } catch (e: Exception) {
            Log.e("insertPatient", e.message.toString())
            ""
        }
    }

    fun getRecords(idPatient: String): Flow<ApiResponse<List<RecordResponse>>> {
        return flow {
            val data = recordDb.get().await()
            val records = data.toObjects(RecordResponse::class.java)
            if (records.isNotEmpty()) {
                val list = records.filter { it.idPatient == idPatient }
                Log.d("getRecords", list.toString())
                emit(ApiResponse.Success(list))
            } else {
                emit(ApiResponse.Success(arrayListOf<RecordResponse>()))
            }
        }
    }

    fun getRecordDetail(id: String): Flow<ApiResponse<RecordResponse>> {
        return flow {
            val idRecord = recordDb.document(id).id
            val data = recordDb.document(id).get().await()
            val record = data.toObject(RecordResponse::class.java) as RecordResponse
            record.id = idRecord
            Log.d("getRecordDetail", record.toString())
            emit(ApiResponse.Success(record))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertRecord(record: RecordResponse): String {
        return try {
            val result = recordDb.add(record)
                .addOnSuccessListener {
                    Log.d("insertRecord", "Saved to DB")
                }
                .addOnFailureListener {
                    Log.d("insertRecord", "Error saving to DB")
                }
                .await()
            result.id
        } catch (e: Exception) {
            Log.e("insertRecord", e.message.toString())
            ""
        }
    }

    fun getStaffDetail(id: String): Flow<ApiResponse<StaffResponse>> {
        return flow {
            val idStaff = patientDb.document(id).id
            val data = staffDb.document(id).get().await()
            val staff = data.toObject(StaffResponse::class.java) as StaffResponse
            staff.id = idStaff
            Log.d("getStaffDetail", staff.toString())
            emit(ApiResponse.Success(staff))
        }.flowOn(Dispatchers.IO)
    }

    fun getStaff(email: String): Flow<ApiResponse<StaffResponse>> {
        return flow {
            val data = staffDb.get().await()
            val staffs = data.toObjects(StaffResponse::class.java)
            if (staffs.isNotEmpty()) {
                var isAvailable = true
                var index = 0
                for (staff in staffs) {
                    if (staff.email == email) {
                        isAvailable = false
                        staff.id = data.documentChanges[index].document.id
                        Log.d("getStaff", staff.toString())
                        emit(ApiResponse.Success(staff))
                        break
                    }
                    index++
                }
                if (isAvailable) {
                    emit(ApiResponse.Success(StaffResponse()))
                }
            } else {
                emit(ApiResponse.Success(StaffResponse()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertStaff(staff: StaffResponse): String {
        return try {
            val result = staffDb.add(staff)
                .addOnSuccessListener {
                    Log.d("insertStaff", "Saved to DB")
                }
                .addOnFailureListener {
                    Log.e("insertStaff", "Error saving to DB")
                }
                .await()
            result.id
        } catch (e: Exception) {
            Log.e("insertStaff", e.message.toString())
            ""
        }
    }
}