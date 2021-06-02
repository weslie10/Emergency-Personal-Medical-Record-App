package com.capstone.personalmedicalrecord.ui.staff.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.usecase.StaffUseCase

class UpdateStaffViewModel(private val staffUseCase: StaffUseCase) : ViewModel() {
    fun getPatient(id: String) = staffUseCase.getStaff(id).asLiveData()
    fun update(staff: Staff) = staffUseCase.updateStaff(staff)
    fun updatePicture(id: String, url: String) = staffUseCase.updatePictureStaff(id, url)
}