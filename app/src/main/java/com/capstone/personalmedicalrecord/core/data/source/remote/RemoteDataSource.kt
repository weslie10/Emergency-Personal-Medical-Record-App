package com.capstone.personalmedicalrecord.core.data.source.remote

import android.util.Log
import com.capstone.personalmedicalrecord.core.data.source.remote.network.ApiResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.PatientResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.StaffResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class RemoteDataSource {
    private val db = FirebaseFirestore.getInstance()

    private val patientDb = db.collection("patient")
    private val staffDb = db.collection("staff")

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

    fun updatePatient(patient: PatientResponse) {
        try {
            patientDb.document(patient.id).set(patient)
                .addOnSuccessListener {
                    Log.d("updatePatient", "Update DB")
                }
                .addOnFailureListener {
                    Log.e("updatePatient", "Error update in DB")
                }
        } catch (e: Exception) {
            Log.e("updatePatient", e.message.toString())
        }
    }

    fun updatePicturePatient(id: String, uri: String) {
        try {
            patientDb.document(id).update(mapOf("picture" to uri))
                .addOnSuccessListener {
                    Log.d("updatePicturePatient", "Success to Change Picture")
                }
                .addOnFailureListener {
                    Log.d("updatePicturePatient", "Error saving to DB")
                }
        } catch (e: Exception) {
            Log.e("updatePicturePatient", e.message.toString())
        }
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

    fun updateStaff(staff: StaffResponse) {
        try {
            staffDb.document(staff.id).set(staff)
                .addOnSuccessListener {
                    Log.d("updateStaff", "Update DB")
                }
                .addOnFailureListener {
                    Log.e("updateStaff", "Error update in DB")
                }
        } catch (e: Exception) {
            Log.e("updateStaff", e.message.toString())
        }
    }

    fun getStaffDetail(id: String): Flow<ApiResponse<StaffResponse>> {
        return flow {
            val idStaff = staffDb.document(id).id
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
}