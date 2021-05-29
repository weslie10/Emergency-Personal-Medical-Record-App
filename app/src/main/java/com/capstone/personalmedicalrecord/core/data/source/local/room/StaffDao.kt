package com.capstone.personalmedicalrecord.core.data.source.local.room

import androidx.room.*
import com.capstone.personalmedicalrecord.core.data.source.local.entity.StaffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffDao {

    @Query("SELECT * FROM staff ORDER BY name")
    fun getStaffs(): Flow<List<StaffEntity>>

    @Query("SELECT * FROM staff WHERE id=:id")
    fun getStaff(id: Int): Flow<StaffEntity>

    @Query("SELECT * FROM staff WHERE email=:email")
    fun getStaff(email: String): Flow<StaffEntity>

    @Query("SELECT * FROM staff WHERE email=:email AND password=:password")
    fun getStaff(email: String, password: String): Flow<StaffEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStaff(staff: StaffEntity): Long

    @Update
    fun updateStaff(staff: StaffEntity)

    @Delete
    fun deleteStaff(staff: StaffEntity)
}