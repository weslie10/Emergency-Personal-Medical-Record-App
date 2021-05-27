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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPatient(patient: PatientEntity)

    @Update
    fun updatePatient(patient: PatientEntity)

    @Delete
    fun deletePatient(patient: PatientEntity)
}