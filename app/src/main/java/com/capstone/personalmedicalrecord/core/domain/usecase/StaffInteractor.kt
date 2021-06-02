package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class StaffInteractor(private val repository: IRepository) : StaffUseCase {
    override fun getStaffs() = repository.getStaffs()

    override fun getStaffDetail(id: String): Flow<Resource<Staff>> = repository.getStaffDetail(id)

    override fun getStaff(email: String): Flow<Resource<Staff>> = repository.getStaff(email)

    override suspend fun insertStaff(staff: Staff) = repository.insertStaff(staff)

    override fun updateStaff(staff: Staff) = repository.updateStaff(staff)

    override fun updatePictureStaff(id: String, url: String) = repository.updatePictureStaff(id, url)

    override fun deleteStaff(id: String) = repository.deleteStaff(id)
}