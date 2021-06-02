package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import kotlinx.coroutines.flow.Flow

interface StaffUseCase {
    fun getStaffs(): Flow<List<Staff>>
    fun getStaffDetail(id: String): Flow<Resource<Staff>>
    fun getStaff(email: String): Flow<Resource<Staff>>
    suspend fun insertStaff(staff: Staff): String
    fun updateStaff(staff: Staff)
    fun updatePictureStaff(id: String, url: String)
    fun deleteStaff(id: String)
}