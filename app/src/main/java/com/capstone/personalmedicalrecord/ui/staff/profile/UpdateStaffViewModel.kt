package com.capstone.personalmedicalrecord.ui.staff.profile

import androidx.lifecycle.ViewModel
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.usecase.StaffUseCase

class UpdateStaffViewModel(private val staffUseCase: StaffUseCase) : ViewModel() {

    fun update(staff: Staff) = staffUseCase.updateStaff(staff)
}