package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class StaffInteractor(private val repository: IRepository) : StaffUseCase {
    override fun getStaffs() = repository.getStaffs()

    override fun getStaff(id: Int): Flow<Staff> = repository.getStaff(id)

    override fun getStaff(email: String): Flow<Staff> = repository.getStaff(email)

    override fun insertStaff(staff: Staff) = repository.insertStaff(staff)

    override fun updateStaff(staff: Staff) = repository.updateStaff(staff)

    override fun deleteStaff(staff: Staff) = repository.deleteStaff(staff)
}