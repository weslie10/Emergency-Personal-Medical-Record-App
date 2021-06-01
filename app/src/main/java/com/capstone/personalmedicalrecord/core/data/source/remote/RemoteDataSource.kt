package com.capstone.personalmedicalrecord.core.data.source.remote

import android.util.Log
import android.widget.Toast
import com.capstone.personalmedicalrecord.core.data.source.remote.network.ApiResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.PatientResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.StaffResponse
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

class RemoteDataSource() {
    private val db = FirebaseFirestore.getInstance()

    fun insertPatient(patient: Patient): String {
        val id = async {
            db.collection("posts")
                .add(patient)
                .addOnSuccessListener {
                    Log.d("insertpatient", "Saved to DB")
                }
                .addOnFailureListener {
                    Log.e("insertpatient", "Error saving to DB")
                }
        }
        return id.await()
    }

    fun getPatient(id: Int): Flow<ApiResponse<PatientResponse>> {
        return flow {
            try {
                db.collection("patient").document(id.toString())
                    .get()
                    .addOnSuccessListener { patient ->
                        runBlocking {
                            emit(
                                ApiResponse.Success(
                                    PatientResponse(
                                        patient.id,
                                        patient.get("name").toString(),
                                        patient.get("password").toString(),
                                        patient.get("phoneNumber").toString(),
                                        patient.get("dateBirth").toString(),
                                        patient.get("address").toString(),
                                        patient.get("gender").toString(),
                                        patient.get("bloodType").toString(),
                                        patient.get("picture").toString(),
                                        patient.get("term").toString()
                                    )
                                )
                            )
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("check User", "Error get patient: ", exception)
                    }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPatient(email: String): Flow<ApiResponse<PatientResponse>> {
        return flow {
            try {
                db.collection("patient").get()
                    .addOnSuccessListener { patients ->
                        var isAvailable = true
                        for (patient in patients) {
                            if (patient.get("email") == email) {
                                isAvailable = false
                                runBlocking {
                                    emit(
                                        ApiResponse.Success(
                                            PatientResponse(
                                                patient.id,
                                                patient.get("name").toString(),
                                                patient.get("password").toString(),
                                                patient.get("phoneNumber").toString(),
                                                patient.get("dateBirth").toString(),
                                                patient.get("address").toString(),
                                                patient.get("gender").toString(),
                                                patient.get("bloodType").toString(),
                                                patient.get("picture").toString(),
                                                patient.get("term").toString()
                                            )
                                        )
                                    )
                                }
                                break
                            }
                        }
                        if (isAvailable) {
                            runBlocking {
                                emit(ApiResponse.Success(PatientResponse()))
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("RemoteDataSource", "Error get patient: ", exception)
                    }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getStaff(id: Int): Flow<ApiResponse<StaffResponse>> {
        return flow {
            try {
                db.collection("patient").document(id.toString())
                    .get()
                    .addOnSuccessListener { staff ->
                        runBlocking {
                            emit(
                                ApiResponse.Success(
                                    StaffResponse(
                                        staff.id,
                                        staff.get("name").toString(),
                                        staff.get("email").toString(),
                                        staff.get("password").toString(),
                                        staff.get("phoneNumber").toString(),
                                        staff.get("hospital").toString(),
                                        staff.get("picture").toString(),
                                    )
                                )
                            )
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("RemoteDataSource", "Error get staff: ", exception)
                    }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getStaff(email: String): Flow<ApiResponse<StaffResponse>> {
        return flow {
            try {
                db.collection("patient").get()
                    .addOnSuccessListener { staffs ->
                        var isAvailable = true
                        for (staff in staffs) {
                            if (staff.get("email") == email) {
                                isAvailable = false
                                runBlocking {
                                    emit(
                                        ApiResponse.Success(
                                            StaffResponse(
                                                staff.id,
                                                staff.get("name").toString(),
                                                staff.get("email").toString(),
                                                staff.get("password").toString(),
                                                staff.get("phoneNumber").toString(),
                                                staff.get("hospital").toString(),
                                                staff.get("picture").toString(),
                                            )
                                        )
                                    )
                                }
                                break
                            }
                        }
                        if (isAvailable) {
                            runBlocking {
                                emit(
                                    ApiResponse.Success(
                                        StaffResponse()
                                    )
                                )
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("check User", "Error get staff: ", exception)
                    }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}