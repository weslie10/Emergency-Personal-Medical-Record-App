package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import kotlinx.coroutines.flow.Flow

interface StaffUseCase {
    fun getStaffs(): Flow<List<Staff>>
    fun getStaff(id: Int): Flow<Resource<Staff>>
    fun getStaff(email: String): Flow<Resource<Staff>>
    fun insertStaff(staff: Staff): Int
    fun updateStaff(staff: Staff)
    fun updatePictureStaff(id: Int, url: String)
    fun deleteStaff(staff: Staff)
}