package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Staff
import kotlinx.coroutines.flow.Flow

interface StaffUseCase {
    fun getStaffs(): Flow<List<Staff>>
    fun getStaff(id: Int): Flow<Staff>
    fun getStaff(email: String): Flow<Staff>
    fun insertStaff(staff: Staff): Flow<Int>
    fun updateStaff(staff: Staff)
    fun deleteStaff(staff: Staff)
}