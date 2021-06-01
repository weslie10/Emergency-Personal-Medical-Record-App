package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class StaffInteractor(private val repository: IRepository) : StaffUseCase {
    override fun getStaffs() = repository.getStaffs()

    override fun getStaff(id: Int): Flow<Resource<Staff>> = repository.getStaff(id)

    override fun getStaff(email: String): Flow<Resource<Staff>> = repository.getStaff(email)

    override fun insertStaff(staff: Staff) = repository.insertStaff(staff)

    override fun updateStaff(staff: Staff) = repository.updateStaff(staff)

    override fun updatePictureStaff(id: Int, url: String) = repository.updatePictureStaff(id, url)

    override fun deleteStaff(staff: Staff) = repository.deleteStaff(staff)
}