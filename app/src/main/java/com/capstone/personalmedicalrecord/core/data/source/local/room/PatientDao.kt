package com.capstone.personalmedicalrecord.core.data.source.local.room

import androidx.room.*
import com.capstone.personalmedicalrecord.core.data.source.local.entity.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Query("SELECT * FROM patient ORDER BY name")
    fun getPatients(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patient WHERE id=:id")
    fun getPatient(id: Int): Flow<PatientEntity>

    @Query("SELECT * FROM patient WHERE email=:email")
    fun getPatient(email: String): Flow<PatientEntity>

    @Query("SELECT * FROM patient WHERE email=:email AND password=:password")
    fun getPatient(email: String, password: String): Flow<PatientEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPatient(patient: PatientEntity): Long

    @Update
    fun updatePatient(patient: PatientEntity)

    @Query("UPDATE patient SET picture=:url WHERE id=:id")
    fun updatePicturePatient(id: Int, url: String)

    @Delete
    fun deletePatient(patient: PatientEntity)
}